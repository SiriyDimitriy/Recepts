package com.anna.recept.builder;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;

public class DetailDtoBuilder {
    
    private DetailDto detailDto = new DetailDto();
    
    public DetailDtoBuilder base(Detail detailEntity){
        detailDto.setId(detailEntity.getId());
        detailDto.setDescription(detailEntity.getDescription());
        return this;
    }
    
    public DetailDto build(){
        return detailDto;
    }
}
