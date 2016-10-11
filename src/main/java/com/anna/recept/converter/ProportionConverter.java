package com.anna.recept.converter;

import com.anna.recept.dto.ProportionDto;
import com.anna.recept.persist.Proportion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProportionConverter {

    public static Proportion toIngridientEntity(ProportionDto propDto, Integer receptId){
        Proportion prop = new Proportion();
        prop.setId(propDto.getId());
        prop.setNorma(propDto.getNorma());
        prop.setIngridientId(propDto.getIngridient().getId());
        prop.setReceptId(receptId);
        return prop;
    }
}
