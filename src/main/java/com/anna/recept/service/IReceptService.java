package com.anna.recept.service;

import com.anna.recept.dto.ReceptDto;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface IReceptService {
    List<ReceptDto> showReceptDtos(Integer departId);

    ReceptDto getRecept(Integer receptId);

    void deleteRecept(Integer receptId);

    Integer saveRecept(ReceptDto recept);

    Integer saveWithUniqueName(ReceptDto recept);

    byte[] saveFile(byte[] file, Integer receptId);

    byte[] getFile(Integer receptId);

    List<ReceptDto> showReceptsByTag(Integer tagId);

    ReceptDto getRecept(String name);

}
