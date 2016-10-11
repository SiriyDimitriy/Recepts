package com.anna.recept.service;

import com.anna.recept.persist.File;

import java.io.IOException;

public interface IFileService {
    void saveFile(File file);
    java.io.File getXsdScheme() throws IOException;
    java.io.File getXslFile() throws IOException;
    java.io.File getLangConfig() throws IOException;
    java.io.File getMiroslavFont() throws IOException;
}
