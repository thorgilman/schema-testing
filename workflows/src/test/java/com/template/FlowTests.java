package com.template;

import com.google.common.collect.ImmutableList;
import com.template.flows.IssueFlow;
import com.template.objects.Beef;
import com.template.objects.GenericProduct;
import com.template.objects.Produce;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.transactions.SignedTransaction;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import net.corda.testing.node.TestCordapp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class FlowTests {
    private final MockNetwork network = new MockNetwork(new MockNetworkParameters(ImmutableList.of(
        TestCordapp.findCordapp("com.template.contracts"),
        TestCordapp.findCordapp("com.template.flows")
    )));
    private final StartedMockNode a = network.createNode();

    public FlowTests() {
        a.registerInitiatedFlow(IssueFlow.ResponderFlow.class);
    }

    @Before
    public void setup() {
        network.runNetwork();
    }

    @After
    public void tearDown() {
        network.stopNodes();
    }

    @Test
    public void testProduce() throws InterruptedException, ExecutionException {
        GenericProduct produce = new Produce("Carrot", "Orange");
        CordaFuture<SignedTransaction> future = a.startFlow(new IssueFlow.InitiatorFlow(produce));
        network.runNetwork();
        future.get();
    }

    @Test
    public void testBeef() {
        GenericProduct beef = new Beef("Austrailian", "10.4");
        CordaFuture<SignedTransaction> future = a.startFlow(new IssueFlow.InitiatorFlow(beef));
        network.runNetwork();
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
