package com.anna.recept.service;


import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.FOPException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface IXmlService {
    Integer getReceptFromXml (MultipartFile file) throws IOException, SAXException, ParserConfigurationException;
    byte[] getPdfFromRecept(Integer receptId) throws TransformerException, IOException, SAXException, ParserConfigurationException, ConfigurationException;
}
