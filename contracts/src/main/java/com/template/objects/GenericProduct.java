package com.template.objects;

import net.corda.core.schemas.MappedSchema;
import net.corda.core.serialization.CordaSerializable;

@CordaSerializable
public interface GenericProduct {
    
    public Class<? extends MappedSchema> getSchema();
    
}
