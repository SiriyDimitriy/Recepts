/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.recept.builder;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;

/**
 *
 * @author Dimitriy
 */
public class DepartDtoBuilder {
    
    private DepartDto depart = new DepartDto();

    public DepartDtoBuilder() {
    }
    
    public DepartDtoBuilder base(final Dapart departEntity) {
        depart.setId(departEntity.getId());
        depart.setName(departEntity.getName());
        return this;
    }
    
    public DepartDto build(){
        return depart;
    }
    
}
