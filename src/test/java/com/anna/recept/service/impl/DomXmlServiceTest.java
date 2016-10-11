package com.anna.recept.service.impl;

import com.anna.recept.persist.Category;
import com.anna.recept.persist.Proportion;
import com.anna.recept.persist.Recept;
import com.anna.recept.persist.Reference;
import com.anna.recept.repository.IFileRepository;
import com.anna.recept.repository.RepositoryTest;
import com.anna.recept.service.IXmlService;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class DomXmlServiceTest extends RepositoryTest {

    @Autowired
    private IXmlService sut;

    @Autowired
    private IFileRepository frep;

    private Integer receptId;
    private String receptName = "Куриные гнёзда";
    private String receptDesc = "Приготовить";
    private String receptDepart = "Вторые блюда";
    private String receptIng = "Яйца";
    private String receptNorma = "7-8 шт";
    private String tag = "Вкусно";
    private String referenceName = "Каша";


    @Override
    public void setUp() throws Exception {
  //      fileRepository.delete(frep.findById(6));

//        com.anna.recept.persist.File ffff = new com.anna.recept.persist.File();
//        ffff.setFileContent(FileUtils.readFileToByteArray(FileUtils.getFile("test.xml")));

//        com.anna.recept.persist.File j = frep.findById(3);
//        j.setFileContent(FileUtils.readFileToByteArray(FileUtils.getFile("Lang.xml")));
//        frep.update(j);
////
//        com.anna.recept.persist.File y = frep.findById(4);
//        y.setFileContent(FileUtils.readFileToByteArray(FileUtils.getFile("MIROSLN.ttf")));
//        frep.update(y);

        super.setUp();
        DEPART_NAME = receptDepart;
        Integer departId = departRepository.save(constructDepart());
        INGRIDIENT_NAME = receptIng;
        ingridientRepository.save(constructIngridient());
        TAG_NAME = tag;
        tagRepository.save(constructTag());
        RECEPT_NAME = referenceName;
        receptRepository.save(constructRecept(departId));
    }

    @Test
    public void shouldGetReceptFromXml() throws IOException, ParserConfigurationException, SAXException, TransformerException, ConfigurationException {
        File file = new File("test.xml");
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Recept><Name>" + receptName + "</Name><Description>" +
                receptDesc + "</Description><Depart>" + receptDepart + "</Depart><Proportions><Proportion><Ingridient>" +
                receptIng + "</Ingridient><Norma>" + receptNorma + "</Norma></Proportion></Proportions>" +
                "<Tags><Tag>" + tag + "</Tag></Tags><References><Reference>" + referenceName + "</Reference></References></Recept>";
        FileUtils.writeStringToFile(file, data);
        Path path = Paths.get(file.getAbsolutePath());
        byte[] mpBytes = Files.readAllBytes(path);
        MultipartFile mpFile = new MockMultipartFile("test.xml", mpBytes);
        file.delete();
        receptId = sut.getReceptFromXml(mpFile);

        assertNotNull(receptId);
        Recept recept = receptRepository.findById(receptId);
        assertThat(recept.getName(), is(receptName));
        assertThat(recept.getText(), is(receptDesc));
        assertThat(departRepository.findById(recept.getDepartId()).getName(), is(receptDepart));

        List<Proportion> proportions = proportionRepository.findByRecept(receptId);
        assertNotNull(proportions);
        assertThat(proportions.size(), is(1));
        assertThat(proportions.get(0).getNorma(), is(receptNorma));
        assertThat(ingridientRepository.findById(proportions.get(0).getIngridientId()).getName(), is(receptIng));

        List<Category> categories = categoryRepository.findByRecept(receptId);
        assertNotNull(categories);
        assertThat(categories.size(), is(1));
        assertThat(tagRepository.findById(categories.get(0).getTagId()).getName(), is(tag));

        List<Reference> references = referenceRepository.findByRecept(receptId);
        assertNotNull(references);
        assertThat(references.size(), is(1));
        assertThat(receptRepository.findById(references.get(0).getReceptReferenceId()).getName(), is(referenceName));
    }

}