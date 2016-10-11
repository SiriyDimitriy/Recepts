package com.anna.recept.service.impl;

import com.anna.recept.builder.ReceptDtoBuilder;
import com.anna.recept.converter.ReceptConverter;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.persist.Recept;
import com.anna.recept.repository.IReceptRepository;
import com.anna.recept.service.IReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceptService implements IReceptService {

    private static final String RECEPT_NAME_NOT_UNIQUE = "Такой рецепт уже есть";
    @Autowired
    private IReceptRepository receptRep;



    @Override
    public List<ReceptDto> showReceptDtos(Integer departId) {
        return showRecepts(departId).stream()
                .map(recept -> new ReceptDtoBuilder()
                        .base(recept)
                        .build())
                .collect(Collectors.toList());
    }

    private List<Recept> showRecepts(Integer departId) {
        if (departId < 0) {
            return receptRep.findAll();
        } else {
            return receptRep.findByDepart(departId);
        }
    }

    @Override
    public ReceptDto getRecept(Integer receptId) {
        return new ReceptDtoBuilder()
                .base(receptRep.findById(receptId))
                .build();
    }

    @Override
    public void deleteRecept(Integer receptId) {
        receptRep.delete(receptRep.findById(receptId));
    }


    @Override
    public Integer saveRecept(ReceptDto recept) {
        if (recept.getId() == null) {
            return receptRep.save(ReceptConverter.toReceptEntity(recept));
        } else {
            receptRep.update(ReceptConverter.toReceptEntity(recept));
            return recept.getId();
        }
    }

    @Override
    public Integer saveWithUniqueName(ReceptDto recept) {
        if (isUniqueReceptName(recept.getName())) {
            return saveRecept(recept);
        }
        throw new ReceptApplicationException(RECEPT_NAME_NOT_UNIQUE);
    }

    @Override
    public byte[] saveFile(byte[] file, Integer receptId) {
        receptRep.updateFile(file, receptId);
        return getFile(receptId);
    }

    @Override
    public byte[] getFile(Integer receptId) {
        return receptRep.findFile(receptId);
    }

    @Override
    public List<ReceptDto> showReceptsByTag(Integer tagId) {
        return receptRep.findByTag(tagId).stream()
                .map(recept -> new ReceptDtoBuilder()
                        .base(recept)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ReceptDto getRecept(String name) {
        if(receptRep.findByName(name) != null) {
            return new ReceptDtoBuilder().base(receptRep.findByName(name)).build();
        }
        return null;
    }

    private boolean isUniqueReceptName(String name) {
        return receptRep.findByName(name) == null;
    }
}
