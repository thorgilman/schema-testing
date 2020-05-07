package com.template.objects;

import com.template.schemas.ProductSchemaV2;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.serialization.CordaSerializable;

@CordaSerializable
public class Beef implements GenericProduct {

    public String type;
    public String weight;

    public Beef(String type, String weight){
        this.type = type;
        this.weight = weight;
    }

    public Class<? extends MappedSchema> getSchema() {
        return ProductSchemaV2.class;
    }
}
