package com.anna.recept.service.impl;

import com.anna.recept.persist.File;
import com.anna.recept.repository.IFileRepository;
import com.anna.recept.service.IFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileService implements IFileService {

    @Autowired
    private IFileRepository fileRep;

    @Override
    public void saveFile(File file) {
        fileRep.save(file);
    }

    @Override
    public java.io.File getXsdScheme() throws IOException {
        return getFileById(1, "recept_scheme.xsd");
    }

    @Override
    public java.io.File getXslFile() throws IOException {
        return getFileById(2, "recept.xsl");
    }

    @Override
    public java.io.File getLangConfig() throws IOException {
        return getFileById(3, "Lang.xml");
    }

    @Override
    public java.io.File getMiroslavFont() throws IOException {
        return getFileById(4, "MIROSLN.ttf");
    }

    private java.io.File getFileById(Integer id, String name) throws IOException {
        java.io.File file = new java.io.File(name);
        FileUtils.writeByteArrayToFile(file, fileRep.findById(id).getFileContent());
        return file;
    }
}
