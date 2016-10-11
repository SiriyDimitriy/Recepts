package com.anna.recept.converter;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailConverter {
    public static Detail toIngridientEntity(DetailDto detailDto){
        Detail detail = new Detail();
        detail.setId(detailDto.getId());
        detail.setDescription(detailDto.getDescription());
        detail.setReceptId(detailDto.getReceptId());
        return detail;
    }
}
