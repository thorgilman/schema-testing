package com.template.states;

import com.google.common.collect.ImmutableList;
import com.template.contracts.ProductContract;
import com.template.objects.Beef;
import com.template.objects.GenericProduct;
import com.template.objects.Produce;
import com.template.schemas.ProductSchemaV1;
import com.template.schemas.ProductSchemaV2;
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
    private final Party party;
    private final GenericProduct product;

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
        if (schema instanceof ProductSchemaV1) {

            // TODO: change
            if (product instanceof Produce) {
                Produce produce = (Produce)product;
                return new ProductSchemaV1.PersistentProductState(linearId.getId(), party, new ProductSchemaV1.PersistentProduce(produce.name, produce.color));
            }

        }
        else if (schema instanceof ProductSchemaV2) {

            if (product instanceof Beef) {
                Beef beef = (Beef)product;
                return new ProductSchemaV2.PersistentProductState2(linearId.getId(), party, new ProductSchemaV2.PersistentBeef(beef.type, beef.weight));
            }
        }
        return null;
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        try {
            MappedSchema schema = product.getSchema().newInstance();
            return ImmutableList.of(schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}