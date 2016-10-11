package com.anna.recept.converter;

import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.persist.Reference;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReferenceConverter {

    public static Reference toReferenceEntity(ReferenceDto refDto, Integer receptId){
        Reference ref = new Reference();
        ref.setId(refDto.getId());
        ref.setReceptId(receptId);
        ref.setReceptReferenceId(refDto.getReceptReferenceId());
        return ref;
    }
}
