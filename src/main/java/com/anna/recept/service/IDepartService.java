package com.anna.recept.service;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;

import java.util.List;

public interface IDepartService {
    DepartDto findDepartmentByReceptId(Integer receptId);
    List<DepartDto> findAllDepartments();
}
