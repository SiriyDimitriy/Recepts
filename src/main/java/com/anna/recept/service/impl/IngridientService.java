package com.anna.recept.service.impl;

import com.anna.recept.builder.IngridientDtoBuilder;
import com.anna.recept.converter.IngridientConverter;
import com.anna.recept.converter.ReferenceConverter;
import com.anna.recept.dto.IngridientDto;
import com.anna.recept.persist.Ingridient;
import com.anna.recept.repository.IIngridientRepository;
import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class IngridientService implements IIngridientService {

    @Autowired
    private IIngridientRepository ingRep;

    @Override
    public Integer saveIngridient(IngridientDto ingridient) {
        Assert.isNull(ingridient.getId());
        return ingRep.save(IngridientConverter.toIngridientEntity(ingridient));
    }

    @Override
    public Integer saveUniqueIngridient(IngridientDto ingridient) {
        if(isUniqueIngridientName(ingridient.getName())) {
            return saveIngridient(ingridient);
        }
        throw new IllegalArgumentException("Ingridient has not unique name");
    }

    private boolean isUniqueIngridientName(String name) {
        return ingRep.findByName(name) == null;
    }

    @Override
    public void deleteIngridient(Integer ingId) {
        ingRep.delete(ingRep.findById(ingId));
    }

    @Override
    public IngridientDto findIngridient(Integer ingridientId) {
        return new IngridientDtoBuilder()
                .base(ingRep.findById(ingridientId))
                .build();
    }

    @Override
    public List<IngridientDto> showAllIngridients() {
        return ingRep.findAll().stream().map(ing -> new IngridientDtoBuilder().base(ing).build())
                .collect(Collectors.toList());
    }

}
