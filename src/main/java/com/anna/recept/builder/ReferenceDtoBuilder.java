package com.anna.recept.builder;

import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.persist.Reference;

public class ReferenceDtoBuilder {
    private ReferenceDto referenceDto = new ReferenceDto();
    
    public ReferenceDtoBuilder base(Reference referenceEntity){
        referenceDto.setId(referenceEntity.getId());
        referenceDto.setReceptReferenceId(referenceEntity.getReceptReferenceId());
        return this;
    }
    
    public ReferenceDtoBuilder withReferenceName(String name){
        referenceDto.setReceptReferenceName(name);
        return this;
    }
    
    public ReferenceDto build(){
        return referenceDto;
    }
}
