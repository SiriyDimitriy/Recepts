package com.anna.recept.service.impl;

import com.anna.recept.converter.DepartConverter;
import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;
import com.anna.recept.repository.IDepartRepository;
import com.anna.recept.service.IDepartService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartService implements IDepartService{
    
    @Autowired
    private IDepartRepository departRep;

    @Override
    public DepartDto findDepartmentByReceptId(Integer receptId) {
        return DepartConverter.toDepartDto(
                departRep.findByRecept(receptId));
    }

    @Override
    public List<DepartDto> findAllDepartments() {
        return departRep.findAll().stream()
                .map(DepartConverter::toDepartDto)
                .collect(Collectors.toList());
    }
}
