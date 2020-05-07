package com.template.objects;

import com.template.schemas.ProductSchemaV1;
import com.template.schemas.ProductSchemaV2;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.serialization.CordaSerializable;

@CordaSerializable
public class Produce implements GenericProduct {

    public String name;
    public String color;

    public Produce(String name, String color){
        this.name = name;
        this.color = color;
    }

    public Class<? extends MappedSchema> getSchema() {
        return ProductSchemaV1.class;
    }

}
