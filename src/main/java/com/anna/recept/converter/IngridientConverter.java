package com.anna.recept.converter;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.persist.Ingridient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngridientConverter {
    public static Ingridient toIngridientEntity(IngridientDto ingDto){
        Ingridient ing = new Ingridient();
        ing.setId(ingDto.getId());
        ing.setName(ingDto.getName());
        return ing;
    }
}
