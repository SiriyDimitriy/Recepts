package com.anna.recept.builder;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.persist.Ingridient;

public class IngridientDtoBuilder {
    
    private IngridientDto ingridient = new IngridientDto();
    
    public IngridientDtoBuilder base(final Ingridient ingridientEntity) {
        ingridient.setId(ingridientEntity.getId());
        ingridient.setName(ingridientEntity.getName());
        return this;
    }
    
    public IngridientDto build(){
        return ingridient;
    }
    
}
