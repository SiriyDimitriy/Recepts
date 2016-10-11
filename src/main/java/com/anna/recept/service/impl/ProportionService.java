package com.anna.recept.service.impl;

import com.anna.recept.converter.ProportionConverter;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.persist.Proportion;
import com.anna.recept.repository.IIngridientRepository;
import com.anna.recept.repository.IProportionRepository;

import java.util.ArrayList;
import java.util.List;

import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anna.recept.service.IProportionService;
import com.anna.recept.builder.ProportionDtoBuilder;
import com.anna.recept.persist.Ingridient;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
public class ProportionService implements IProportionService {

    @Autowired
    private IProportionRepository propRep;
    @Autowired
    private IIngridientService ingServ;

    @Override
    public List<ProportionDto> findReceptsProportions(Integer receptId) {
        return propRep.findByRecept(receptId).stream()
                .map(prop -> new ProportionDtoBuilder()
                        .base(prop)
                        .withIngridient(ingServ.findIngridient(prop.getIngridientId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProportions(Integer receptId) {
        propRep.findByRecept(receptId).stream().forEach(propRep::delete);
    }

    @Override
    public int saveProportion(ProportionDto proportion, Integer receptId) {
        Assert.notNull(proportion.getIngridient());
        Assert.notNull(proportion.getIngridient().getId());

        if(proportion.getId() == null) {
            return propRep.save(ProportionConverter.toIngridientEntity(proportion, receptId));
        }

        propRep.update(ProportionConverter.toIngridientEntity(proportion, receptId));
        return proportion.getId();
    }

    @Override
    public void deleteProportion(Integer id) {
        propRep.delete(propRep.findById(id));
    }

}
