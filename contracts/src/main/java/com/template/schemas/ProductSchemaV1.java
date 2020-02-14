package com.template.schemas;

import com.google.common.collect.ImmutableList;
import com.template.states.ProductState;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.serialization.CordaSerializable;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

public class ProductSchemaV1 extends MappedSchema {


    public ProductSchemaV1() {
        super(ProductSchema.class, 1, ImmutableList.of(PersistentProductState.class));
    }

    @Entity
    @Table(name="productState")
    public static class PersistentProductState extends PersistentState {
        @Column(name = "linear_id") @Type(type = "uuid-char") private final UUID linearId;
        @Column(name = "party") private final Party party;

        @OneToMany(cascade = CascadeType.PERSIST)
        @JoinColumns({
                @JoinColumn(name = "productState_output_index", referencedColumnName = "output_index"),
                @JoinColumn(name = "productState_transaction_id", referencedColumnName = "transaction_id")
        })
        private final PersistentProduce productState;

        public PersistentProductState(UUID linearId, Party party, PersistentProduce productState) {
            this.linearId = linearId;
            this.party = party;
            this.productState = productState;
        }
    }



    @Entity
    @CordaSerializable
    @Table(name = "produce")
    public static class PersistentProduce {

        @Id private final UUID Id;
        @Column(name = "name") private final String name;
        @Column(name = "color") private final String color;

        @ManyToOne(targetEntity = PersistentProductState.class)
        private final PersistentProductState productState;

        public PersistentProduce(String name, String color) {
            this.Id = UUID.randomUUID();
            this.name = name;
            this.color = color;
            this.productState = null; // TODO: ?
        }

    }


}
