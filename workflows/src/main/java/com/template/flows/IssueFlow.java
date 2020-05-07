package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.ProductContract;
import com.template.objects.GenericProduct;
import com.template.objects.Produce;
import com.template.states.ProductState;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.StateRef;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.corda.core.contracts.ContractsDSL.requireThat;

public class IssueFlow {

    @InitiatingFlow
    @StartableByRPC
    public static class InitiatorFlow extends FlowLogic<SignedTransaction> {

        private GenericProduct genericProduct;

        public InitiatorFlow(GenericProduct genericProduct) {
            this.genericProduct = genericProduct;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {

            final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

            ProductState productState = new ProductState(getOurIdentity(), genericProduct);
            Command issueCommand = new Command<>(new ProductContract.Commands.Issue(), productState.getParticipants().stream().map(it->it.getOwningKey()).collect(Collectors.toList()));

            final TransactionBuilder builder = new TransactionBuilder(notary);
            builder.addOutputState(productState);
            builder.addCommand(issueCommand);
            builder.verify(getServiceHub());

            SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(builder);

            List<Party> otherParties = Collections.emptyList(); // TODO: Update

            List<FlowSession> sessions = otherParties.stream().map(el -> initiateFlow(el)).collect(Collectors.toList());
            SignedTransaction finalSignedTransaction = subFlow(new CollectSignaturesFlow(signedTransaction, sessions));
            return subFlow(new FinalityFlow(finalSignedTransaction, sessions));

        }
    }

    @InitiatedBy(IssueFlow.InitiatorFlow.class)
    public static class ResponderFlow extends FlowLogic<SignedTransaction> {
        private FlowSession counterpartySession;

        public ResponderFlow(FlowSession counterpartySession) {
            this.counterpartySession = counterpartySession;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {

            class SignTxFlow extends SignTransactionFlow {
                private SignTxFlow(FlowSession otherPartySession) {
                    super(otherPartySession);
                }

                @Override
                protected void checkTransaction(SignedTransaction stx) {
                    requireThat(require -> {
                        ContractState output = stx.getTx().getOutputs().get(0).getData();
                        return null;
                    });
                }
            }
            SecureHash expectedTxId = subFlow(new SignTxFlow(counterpartySession)).getId();
            return subFlow(new ReceiveFinalityFlow(counterpartySession, expectedTxId));
        }}

}