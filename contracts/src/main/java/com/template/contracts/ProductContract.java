package com.template.contracts;

import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

public class ProductContract implements Contract {

    public static final String ID = "com.template.contracts.ProductContract";


    @Override
    public void verify(LedgerTransaction tx) {
        // TODO
    }

    public interface Commands extends CommandData {
        class Issue implements Commands {}
        class Transfer implements Commands {}
    }
}
