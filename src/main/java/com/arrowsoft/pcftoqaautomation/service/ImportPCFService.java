package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Log4j2
@Service
public class ImportPCFService {

    private final PCFRepository pcfRepository;
    private final WidgetRepository widgetRepository;

    public ImportPCFService(PCFRepository pcfRepository, WidgetRepository widgetRepository) {
        this.pcfRepository = pcfRepository;
        this.widgetRepository = widgetRepository;

    }

    public void readPCF(File pcfFile) throws IOException, SAXException, ParserConfigurationException {
        var containerElement = getContainerElementFromFile(pcfFile);
        if (containerElement == null) {
            log.info("Container element not found");
            return;
        }
        var pcf = new PCFEntity(pcfFile.getName(), pcfFile.getPath());
        savePCF(pcf);
        var containerWidget = new WidgetEntity(pcf, null, containerElement); //TODO new WidgetTypeEntity()
        saveWidget(containerWidget);
        readNodeChildren(pcf, containerElement, containerWidget);

    }

    private Element getContainerElementFromFile(File pcfFile) throws ParserConfigurationException, IOException, SAXException {
        if (pcfFile == null || !pcfFile.exists()) {
            log.info("File not found");
            return null;
        }
        var rootElement = getRootElementFromFile(pcfFile);
        var pcfNodeList = rootElement.getChildNodes();
        var pcfNodeListLength = pcfNodeList.getLength();
        for (int temp = 0; temp < pcfNodeListLength; temp++) {
            var node = pcfNodeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) node;

            }

        }
        return null;

    }

    private Element getRootElementFromFile(File pcfFile) throws ParserConfigurationException, IOException, SAXException {
        var dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var docElement = dBuilder.parse(pcfFile).getDocumentElement();
        docElement.normalize();
        return docElement;

    }

    private void readNodeChildren(PCFEntity pcf, Element parentElement, WidgetEntity parentWidget) {
        var childNodes = parentElement.getChildNodes();
        var childNodesLength = childNodes.getLength();
        if (childNodesLength == 0) {
            return;
        }
        for (int temp = 0; temp < childNodesLength; temp++) {
            var node = childNodes.item(temp);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            var element = (Element) node;
            var widget = new WidgetEntity(pcf, null, element, parentWidget); //TODO new WidgetTypeEntity()
            saveWidget(widget);
            readNodeChildren(pcf, element, widget);

        }

    }

    private void saveWidget(WidgetEntity widget) {
        logWidgetEntityFields(widget);
        widgetRepository.save(widget);

    }

    private void savePCF(PCFEntity pcf) {
        logPCFEntityFields(pcf);
        pcfRepository.save(pcf);
    }

    private void logWidgetEntityFields(WidgetEntity widget) {
        if (!log.isInfoEnabled()) {
            return;
        }
//TODO        log.info("Widget Tag: " + widget.getWidgetType().getType());
        log.info("Widget ID: " + widget.getWidgetPCFID());
        log.info("Widget Ref: " + widget.getRefPCF());
        log.info("Widget RenderID: " + widget.getRenderID());
        log.info("---------------------------------------");

    }

    private void logPCFEntityFields(PCFEntity pcf) {
        if (!log.isInfoEnabled()) {
            return;
        }
        log.info("PCF File Name" + pcf.getPcfName());
        log.info("PCF File Path" + pcf.getPcfFilePath());

    }

}
