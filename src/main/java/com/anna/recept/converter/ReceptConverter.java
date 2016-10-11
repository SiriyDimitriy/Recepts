package com.anna.recept.converter;

import com.anna.recept.dto.ReceptDto;
import com.anna.recept.persist.Detail;
import com.anna.recept.persist.Proportion;
import com.anna.recept.persist.Recept;
import com.anna.recept.persist.Reference;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceptConverter {

    public static Recept toReceptEntity(ReceptDto receptDto){
        Recept recept = new Recept();
        recept.setId(receptDto.getId());
        recept.setName(receptDto.getName());
        recept.setText(receptDto.getText());
        recept.setDepartId(receptDto.getDepartId().getId());
        return recept;
    }

    public static List<Detail> toDetailList(ReceptDto receptDto){
        List<Detail> details = new ArrayList<>();
        receptDto.getDetails().forEach(detailDto->{
            Detail detail = new Detail();
            detail.setId(detailDto.getId());
            detail.setDescription(detailDto.getDescription());
            detail.setReceptId(receptDto.getId());
            details.add(detail);
        });
        return details;
    }

    public static List<Proportion> toProportionList(ReceptDto receptDto){
        List<Proportion> proportions = new ArrayList<>();
        receptDto.getProportions().forEach(propDto->{
            Proportion proportion = new Proportion();
            proportion.setId(propDto.getId());
            proportion.setNorma(propDto.getNorma());
            proportion.setIngridientId(propDto.getIngridient().getId());
            proportion.setReceptId(receptDto.getId());
            proportions.add(proportion);
        });
        return proportions;
    }

    public static List<Reference> toReferenceList(ReceptDto receptDto){
        List<Reference> references = new ArrayList<>();
        receptDto.getReferences().forEach(refDto->{
            Reference reference = new Reference();
            reference.setId(refDto.getId());
            reference.setReceptId(receptDto.getId());
            reference.setReceptReferenceId(refDto.getReceptReferenceId());
            references.add(reference);
        });
        return references;
    }
}
