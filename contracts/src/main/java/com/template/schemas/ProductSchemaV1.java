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
        super(ProductSchema.class, 1, ImmutableList.of(PersistentProductState.class, PersistentProduce.class));
    }

//    @Entity
//    @Table(name="product")
//    public static class PersistentProductState2 extends PersistentState {
//        @Column(name = "linear_id") @Type(type = "uuid-char") private final UUID linearId;
//        @Column(name = "party") private final Party party;
//
//        @OneToOne(cascade = CascadeType.PERSIST)
////        @JoinColumns({
////            @JoinColumn(name = "productState_output_index", referencedColumnName = "output_index"),
////            @JoinColumn(name = "productState_transaction_id", referencedColumnName = "transaction_id")
////        })
//        private final PersistentBeef state;
//
//        public PersistentProductState2(UUID linearId, Party party, PersistentBeef state) {
//            this.linearId = linearId;
//            this.party = party;
//            this.state = state;
//        }
//    }

    @Entity(name="productProduce")
    @Table(name="product")
    public static class PersistentProductState extends PersistentState {
        @Column(name = "linear_id") @Type(type = "uuid-char") private final UUID linearId;
        @Column(name = "party") private final Party party;

        @OneToOne(cascade = CascadeType.PERSIST)
//        @JoinColumns({
//            @JoinColumn(name = "productState_output_index", referencedColumnName = "output_index"),
//            @JoinColumn(name = "productState_transaction_id", referencedColumnName = "transaction_id")
//        })
        private final PersistentProduce state;

        public PersistentProductState(UUID linearId, Party party, PersistentProduce state) {
            this.linearId = linearId;
            this.party = party;
            this.state = state;
        }
    }






    @Entity
    @CordaSerializable
    @Table(name = "produce")
    public static class PersistentProduce {

        @Id private final UUID Id;
        @Column(name = "name") private final String name;
        @Column(name = "color") private final String color;

        @OneToOne(targetEntity = PersistentProductState.class)
        private final ProductState productState;

        public PersistentProduce(String name, String color) {
            this.Id = UUID.randomUUID();
            this.name = name;
            this.color = color;
            this.productState = null; // TODO: ?
        }
    }


//    @Entity
//    @CordaSerializable
//    @Table(name = "beef")
//    public static class PersistentBeef {
//
//        @Id private final UUID Id;
//        @Column(name = "type") private final String type;
//        @Column(name = "weight") private final String weight;
//
//        @OneToOne(targetEntity = PersistentProductState2.class)
//        private final ProductState productState;
//
//        public PersistentBeef(String type, String weight) {
//            this.Id = UUID.randomUUID();
//            this.type = type;
//            this.weight = weight;
//            this.productState = null; // TODO: ?
//        }
//    }


}