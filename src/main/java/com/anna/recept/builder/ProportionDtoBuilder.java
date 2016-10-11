package com.anna.recept.builder;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.persist.Ingridient;
import com.anna.recept.persist.Proportion;

/**
 *
 * @author Dimitriy
 */
public class ProportionDtoBuilder {
    
    private IngridientDtoBuilder ingridientDtoBuilder = new IngridientDtoBuilder();
    
    private ProportionDto proportionDto = new ProportionDto();
    
    public ProportionDtoBuilder base(Proportion proportionEntity){
        proportionDto.setId(proportionEntity.getId());
        proportionDto.setNorma(proportionEntity.getNorma());
        return this;
    }
    
    public ProportionDtoBuilder withIngridient (final IngridientDto ingridient){
        proportionDto.setIngridient(ingridient);
        return this;
    }
    
    public ProportionDto build(){
        return proportionDto;
    }
}
