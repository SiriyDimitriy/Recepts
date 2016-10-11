package com.anna.recept.service.impl;

import com.anna.recept.builder.DetailDtoBuilder;
import com.anna.recept.converter.DetailConverter;
import com.anna.recept.converter.ProportionConverter;
import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import com.anna.recept.repository.IDetailRepository;
import com.anna.recept.service.IDetailService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DetailService implements IDetailService {

    @Autowired
    private IDetailRepository detRep;

    @Override
    public List<DetailDto> findReceptsDetails(Integer receptId) {
        return detRep.findByRecept(receptId).stream()
                .map(detail -> new DetailDtoBuilder()
                        .base(detail)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDetails(Integer receptId) {
        detRep.findByRecept(receptId).stream().forEach(detRep::delete);
    }

    @Override
    public int saveDetail(DetailDto detail) {
        if(detail.getId() == null) {
            return detRep.save(DetailConverter.toIngridientEntity(detail));
        }

        detRep.update(DetailConverter.toIngridientEntity(detail));
        return detail.getId();
    }

    @Override
    public void deleteDetail(Integer id) {
        detRep.delete(detRep.findById(id));
    }


    @Override
    public byte[] saveFile(byte[] file, Integer detailId) {
        detRep.updateFile(file, detailId);
        return getFile(detailId);
    }

    @Override
    public byte[] getFile(Integer detailId) {
        return detRep.findFile(detailId);
    }
}
