package com.anna.recept.service.impl;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.dto.ReceptXmlDto;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.IDepartService;
import com.anna.recept.service.IFileService;
import com.anna.recept.service.IIngridientService;
import com.anna.recept.service.IProportionService;
import com.anna.recept.service.IReceptService;
import com.anna.recept.service.IReferenceService;
import com.anna.recept.service.ITagService;
import com.anna.recept.service.IXmlService;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.xpath.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DomXmlService implements IXmlService {

    @Autowired
    private IReceptService receptService;

    @Autowired
    private IDepartService departService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IReferenceService refService;

    @Autowired
    private IIngridientService ingService;

    @Autowired
    private IProportionService propService;

    @Autowired
    private IFileService fileService;

    private static final Logger LOG = Logger.getLogger(DomXmlService.class);

    @Override
    public byte[] getPdfFromRecept(Integer receptId) throws TransformerException, IOException, SAXException, ParserConfigurationException, ConfigurationException {
        String xmlName = UUID.randomUUID().toString().concat(".xml");
        File xml = new File(xmlName);
        constructXml(getXmlDto(receptId), xml);
        File pdf = createPdf(xml, receptService.getFile(receptId));
        xml.delete();
        Path path = Paths.get(pdf.getAbsolutePath());
        byte[] result = Files.readAllBytes(path);
        pdf.delete();
        return result;
    }

    private ReceptXmlDto getXmlDto(Integer receptId) {
        ReceptXmlDto recept = new ReceptXmlDto();
        ReceptDto receptDto = receptService.getRecept(receptId);
        recept.setName(receptDto.getName());
        recept.setText(receptDto.getText());
        recept.setDepartName(departService.findDepartmentByReceptId(receptId).getName());
        List<String> tags = new ArrayList<>();
        categoryService.findTagsByRecept(receptId).stream()
                .forEach((tag) -> tags.add(tag.getName()));
        if (!tags.isEmpty()) {
            recept.setTags(tags);
        }

        List<String> refs = new ArrayList<>();
        refService.findReceptsReferences(receptId).stream()
                .forEach((ref) -> {
                    refs.add(ref.getReceptReferenceName());
                });
        if (!refs.isEmpty()) {
            recept.setReferences(refs);
        }

        Map<String, String> proportions = new HashMap<>();
        propService.findReceptsProportions(receptId).stream()
                .forEach((prop) -> {
                    proportions.put(prop.getIngridient().getName(), prop.getNorma());
                });
        if (!proportions.isEmpty()) {
            recept.setProportions(proportions);
        }
        return recept;
    }

    private void constructXml(ReceptXmlDto recept, File sourceXml) throws ParserConfigurationException, TransformerException, TransformerConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        // root element
        Element rootElement = doc.createElement("Recept");
        doc.appendChild(rootElement);

        //   element
        Element name = doc.createElement("Name");
        name.appendChild(doc.createTextNode(recept.getName()));
        rootElement.appendChild(name);

        if (recept.getText() != null) {
            Element description = doc.createElement("Description");
            description.appendChild(doc.createTextNode(recept.getText()));
            rootElement.appendChild(description);
        }

        Element depart = doc.createElement("Depart");
        depart.appendChild(doc.createTextNode(recept.getDepartName()));
        rootElement.appendChild(depart);

        if (recept.getTags() != null) {
            Element tags = doc.createElement("Tags");
            recept.getTags().stream().forEach((tag) -> {
                Element tagEl = doc.createElement("Tag");
                tagEl.appendChild(doc.createTextNode(tag));
                tags.appendChild(tagEl);
            });
            rootElement.appendChild(tags);
        }

        if (recept.getReferences() != null) {
            Element references = doc.createElement("References");
            recept.getReferences().stream().forEach((ref) -> {
                Element refEl = doc.createElement("Reference");
                refEl.appendChild(doc.createTextNode(ref));
                references.appendChild(refEl);
            });
            rootElement.appendChild(references);
        }

        if (recept.getProportions() != null) {
            Element proportions = doc.createElement("Proportions");
            for (Map.Entry<String, String> entry : recept.getProportions().entrySet()) {
                Element proportion = doc.createElement("Proportion");

                Element ingridient = doc.createElement("Ingridient");
                ingridient.appendChild(doc.createTextNode(entry.getKey()));
                proportion.appendChild(ingridient);
                if (entry.getValue() != null) {
                    Element norma = doc.createElement("Norma");
                    norma.appendChild(doc.createTextNode(entry.getValue()));
                    proportion.appendChild(norma);
                }
                proportions.appendChild(proportion);
            }
            rootElement.appendChild(proportions);
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(sourceXml.getAbsolutePath());
        transformer.transform(source, result);

    }

    private File createPdf(File sourceXml, byte[] picture) throws IOException, SAXException, TransformerException, ConfigurationException {
//        com.anna.recept.persist.File j = fileR.findById(3);
//        j.setFileContent(FileUtils.readFileToByteArray(FileUtils.getFile("Lang.xml")));
//        fileR.update(j);
////
//        com.anna.recept.persist.File y = fileR.findById(4);
//        y.setFileContent(FileUtils.readFileToByteArray(FileUtils.getFile("MIROSLN.ttf")));
//        fileR.update(y);

        String pdfName = UUID.randomUUID().toString().concat(".pdf");

        File pdffile = new File(pdfName);

        File xsltfile = fileService.getXslFile();
        File langConfig = fileService.getLangConfig();
        File font = fileService.getMiroslavFont();

        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        Configuration cfg = cfgBuilder.buildFromFile(langConfig);
        FopFactory fopFactory = FopFactory.newInstance();
        fopFactory.setUserConfig(cfg);
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        OutputStream out = new java.io.FileOutputStream(pdffile);
        out = new java.io.BufferedOutputStream(out);

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltfile.getAbsolutePath()));

            transformer.setParameter("versionParam", "1.0");

            Source src = new StreamSource(sourceXml);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformToPdf(picture, src, res, transformer);

        } finally {
            out.close();
            langConfig.delete();
            font.delete();
            xsltfile.delete();
        }

        return pdffile;
    }

    synchronized private void transformToPdf(byte[] picture, Source src, Result res, Transformer transformer) {
        File image = new File("picture.png");
        try {
            //TODO: add default picture
            if (picture != null) {
                FileUtils.writeByteArrayToFile(image, picture);
            }
            transformer.transform(src, res);
        } catch (IOException | TransformerException e) {
            LOG.error("transformToPdf method failed");
            LOG.error(e.getMessage());
        } finally {
            image.delete();
        }
    }

    @Override
    public Integer getReceptFromXml(MultipartFile file) throws IOException, SAXException, ParserConfigurationException {
        String xmlName = UUID.randomUUID().toString();
        File xml = new File(xmlName);
        file.transferTo(xml);

        checkXSD(xml);

        Document doc = getDocument(xml);
        xml.delete();

        ReceptXmlDto dto = constructDto(doc);

        return saveData(dto);
    }

    private Integer saveData(ReceptXmlDto dto) {
        ReceptDto recept = new ReceptDto();

        departService.findAllDepartments().stream()
                .filter((depart) -> depart.getName().equals(dto.getDepartName()))
                .findFirst().ifPresent((depart) -> {
            DepartDto departDto = new DepartDto();
            departDto.setId(depart.getId());
            recept.setDepartId(departDto);
        });

        if (recept.getDepartId() == null) {
            throw new ReceptApplicationException("Нет такого раздела");
        }

        recept.setName(dto.getName());
        recept.setText(dto.getText());

        Integer id = receptService.saveWithUniqueName(recept);

        if (id == null) {
            return null;
        }

        dto.getTags().stream().forEach((tag) -> {
            tagService.findTags().stream().filter((availableTag) -> availableTag.getName().equalsIgnoreCase(tag))
                    .findFirst()
                    .ifPresent((filteredTag) -> categoryService.saveCategory(id, filteredTag.getId()));
        });

        dto.getReferences().stream().forEach((ref) -> {
            if (receptService.getRecept(ref) != null) {
                ReferenceDto reference = new ReferenceDto();
                reference.setReceptReferenceId(receptService.getRecept(ref).getId());
                refService.saveReference(reference, id);
            }
        });

        for (Map.Entry<String, String> entry : dto.getProportions().entrySet()) {
            ingService.showAllIngridients().stream()
                    .filter((ing) -> ing.getName().equalsIgnoreCase(entry.getKey()))
                    .findFirst().ifPresent((ingridient) -> {
                ProportionDto proportion = new ProportionDto();
                proportion.setNorma(entry.getValue());
                IngridientDto ingridientDto = new IngridientDto();
                ingridientDto.setId(ingridient.getId());
                proportion.setIngridient(ingridientDto);
                propService.saveProportion(proportion, id);
            });
        }

        return id;
    }

    private void checkXSD(File xml) throws SAXException, IOException {
        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = schemaFactory.newSchema(fileService.getXsdScheme());
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
        } catch (SAXException e) {
            throw new IllegalArgumentException("XML file not match XSD scheme");
        }
    }

    private ReceptXmlDto constructDto(Document doc) {
        ReceptXmlDto recept = new ReceptXmlDto();

        recept.setName(doc.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
        recept.setDepartName(doc.getElementsByTagName("Depart").item(0).getFirstChild().getNodeValue());
        recept.setText(doc.getElementsByTagName("Description").item(0).getFirstChild().getNodeValue());

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < doc.getElementsByTagName("Tag").getLength(); i++) {
            Element tagEl = (Element) doc.getElementsByTagName("Tag").item(i);
            tags.add(tagEl.getFirstChild().getNodeValue());
        }
        recept.setTags(tags);

        List<String> references = new ArrayList<>();
        for (int i = 0; i < doc.getElementsByTagName("Reference").getLength(); i++) {
            Element refEl = (Element) doc.getElementsByTagName("Reference").item(i);
            references.add(refEl.getFirstChild().getNodeValue());
        }
        recept.setReferences(references);

        Map<String, String> proportions = new HashMap<>();
        for (int i = 0; i < doc.getElementsByTagName("Proportion").getLength(); i++) {
            Element proportionEl = (Element) doc.getElementsByTagName("Proportion").item(i);
            String ing = proportionEl.getElementsByTagName("Ingridient").item(0).getFirstChild().getNodeValue();
            String norma = null;
            if (proportionEl.getElementsByTagName("Norma").item(0) != null && proportionEl.getElementsByTagName("Norma").item(0).getFirstChild() != null) {
                norma = proportionEl.getElementsByTagName("Norma").item(0).getFirstChild().getNodeValue();
            }
            proportions.put(ing, norma);
        }
        recept.setProportions(proportions);
        return recept;
    }

    private Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        return db.parse(file);
    }

}
