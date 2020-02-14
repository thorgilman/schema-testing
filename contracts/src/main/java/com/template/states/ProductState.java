package com.template.states;

import com.template.contracts.ProductContract;
import com.template.objects.GenericProduct;
import net.corda.core.contracts.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@BelongsToContract(ProductContract.class)
public class ProductState implements ContractState, LinearState, QueryableState {

    private final UniqueIdentifier linearId;
    private final GenericProduct product;
    private final Party party;

    public ProductState(Party party, GenericProduct product) {
        this.linearId = new UniqueIdentifier();
        this.party = party;
        this.product = product;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(party);
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }


    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {
        return null;
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return null;
    }
}