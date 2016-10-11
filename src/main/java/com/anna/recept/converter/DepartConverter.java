package com.anna.recept.converter;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Hanna_Sira on 5/4/2016.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartConverter {

    public static DepartDto toDepartDto(Dapart depart){
        DepartDto departDto = new DepartDto();
        departDto.setId(depart.getId());
        departDto.setName(depart.getName());
        return departDto;
    }
}
