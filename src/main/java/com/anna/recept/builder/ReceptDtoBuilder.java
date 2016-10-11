package com.anna.recept.builder;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.persist.Dapart;
import com.anna.recept.persist.Recept;
import java.util.List;

public class ReceptDtoBuilder {
    
    private DepartDtoBuilder departDtoBuilder = new DepartDtoBuilder();
    
    private ReceptDto recept = new ReceptDto();
    
    public ReceptDtoBuilder base(Recept receptEntity){
        recept.setId(receptEntity.getId());
        recept.setName(receptEntity.getName());
        recept.setText(receptEntity.getText());
        return this;
    }
    
    public ReceptDtoBuilder withDepart (final Dapart departEntity){
        recept.setDepartId(departDtoBuilder.base(departEntity).build());
        return this;
    }
    
    public ReceptDtoBuilder withProportions (final List<ProportionDto> proportions){
        recept.setProportions(proportions);
        return this;
    }
    
    public ReceptDtoBuilder withDetails (final List<DetailDto> details){
        recept.setDetails(details);
        return this;
    }
    
    public ReceptDtoBuilder withReferences (final List<ReferenceDto> refs){
        recept.setReferences(refs);
        return this;
    }
    
    public ReceptDto build(){
        return recept;
    }
}
