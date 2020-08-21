package com.arrowsoft.pcftoqaautomation.batch.importpcf.util;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchMsg;
import com.arrowsoft.pcftoqaautomation.entity.*;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Log4j2
@Component
public class ImportPCFBathUtil {

    private final EnumRepository enumRepository;

    public ImportPCFBathUtil(EnumRepository enumRepository) {
        this.enumRepository = enumRepository;
    }

    public Iterator<File> getPCFFiles(ProjectEntity project) {
        return getFiles(project, ImportPCFBatchConst.PCF_EXTENSION_FILES, ImportPCFBatchConst.PCF_PATH);

    }

    public Iterator<File> getTypeCodeMetadataFiles(ProjectEntity project) {
        return getFiles(project, ImportPCFBatchConst.TYPECODE_EXTENSION_FILES_META, ImportPCFBatchConst.TYPECODE_METADATA_PATH);

    }

    public Iterator<File> getTypeCodeExtensionFiles(ProjectEntity project) {
        return getFiles(project, ImportPCFBatchConst.TYPECODE_EXTENSION_FILES_EXT, ImportPCFBatchConst.TYPECODE_EXTENSION_PATH);

    }

    private Iterator<File> getFiles(ProjectEntity project, String[] extensionFiles, String path) {
        if (project == null) {
            log.error(SharedBatchMsg.ERROR_PROJECT_NOT_FOUND);
            return null;

        }
        var rootFolder = new File(project.getRootPath() + path);
        if (!rootFolder.exists()) {
            log.error(SharedBatchMsg.ERROR_FOLDER_NOT_FOUND);
            return null;

        }
        return FileUtils.iterateFiles(rootFolder, extensionFiles, true);
    }

    public PCFEntity createPCFEntity(ImportPCFBatchTransport importPcfBatchTransport,
                                     Set<WidgetTypeEntity> widgetTypeSet) throws IOException, SAXException, ParserConfigurationException {
        var file = importPcfBatchTransport.getFile();
        var project = importPcfBatchTransport.getProject();
        var containerElement = getContainerElementFromFile(file);
        if (containerElement == null) {
            log.error(SharedBatchMsg.ERROR_CONTAINER_ELEMENT_NOT_FOUND);
            return null;
        }
        var widgetType = getWidgetTypeEntity(widgetTypeSet, WidgetTypeEnum.valueOf(containerElement.getTagName()));
        if (widgetType == null) {
            log.error("Widget type not found: " + containerElement.getTagName());
            return null;

        }
        var pcf = new PCFEntity(project, getFileName(file), getRelativeFilePath(file), widgetType, containerElement);
        readNodeChildren(pcf, widgetTypeSet, containerElement, null);
        return pcf;

    }

    public EnumEntity createEnumEntity(ImportPCFBatchTransport importPcfBatchTransport) throws ParserConfigurationException, SAXException, IOException {
        var file = importPcfBatchTransport.getFile();
        var project = importPcfBatchTransport.getProject();
        var value = getValuesFromTypeCodeFile(file);
        return new EnumEntity(project, getFileName(file), file.getName(), value);

    }

    private String getFileName(File file) {
        return file.getName().split("\\.")[0];

    }

    private String getRelativeFilePath(File file) {
        var folderPath = file.getParent();
        return folderPath.substring(folderPath.indexOf("\\pcf") + 4);

    }

    private WidgetEntity createWidget(PCFEntity pcf, Set<WidgetTypeEntity> widgetTypeSet, Element element, WidgetEntity parent) {
        try {
            var tagEnum = WidgetTypeEnum.valueOf(element.getTagName());
        } catch (Exception e) {
            log.error("WIDGET NOT FOUND: " + element.getTagName());
            return null;

        }
        var widgetType = getWidgetTypeEntity(widgetTypeSet, WidgetTypeEnum.valueOf(element.getTagName()));
        if (widgetType == null) {
            log.error("Widget type not found: " + element.getTagName());
            return null;

        }
        var widgetEntity = new WidgetEntity(pcf, widgetType, element, parent);
        if (widgetEntity.isNeedCustomEnum() && !this.enumRepository.existsByProjectAndName(pcf.getProject(), widgetEntity.getEnumRef())) {
            this.enumRepository.saveAndFlush(new EnumEntity(pcf.getProject(), widgetEntity.getEnumRef(), widgetEntity.getEnumRef(), null, true));

        }
        pcf.getWidgets().add(widgetEntity);
        return widgetEntity;

    }

    private WidgetTypeEntity getWidgetTypeEntity(Set<WidgetTypeEntity> widgetTypeSet, WidgetTypeEnum widgetType) {
        return widgetTypeSet.stream().filter(wt -> wt.getType() == widgetType).findFirst().orElse(null);

    }

    private Element getContainerElementFromFile(File pcfFile) throws ParserConfigurationException, IOException, SAXException {
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

    private Element getRootElementFromFile(File file) throws ParserConfigurationException, IOException, SAXException {
        var dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var docElement = dBuilder.parse(file).getDocumentElement();
        docElement.normalize();
        return docElement;

    }

    private void readNodeChildren(PCFEntity pcf, Set<WidgetTypeEntity> widgetTypeSet, Element parentElement, WidgetEntity parentWidget) {
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
            var widget = createWidget(pcf, widgetTypeSet, element, parentWidget);
            if (widget == null) {
                continue;

            }
            readNodeChildren(pcf, widgetTypeSet, element, widget);

        }

    }

    private Set<String> getValuesFromTypeCodeFile(File file) throws IOException, SAXException, ParserConfigurationException {
        var elementRoot = getRootElementFromFile(file);
        var childNodes = elementRoot.getChildNodes();
        var childNodesLength = childNodes.getLength();
        if (childNodesLength == 0) {
            return null;

        }
        var joiner = new HashSet<String>();
        for (int temp = 0; temp < childNodesLength; temp++) {
            var node = childNodes.item(temp);
            if (node.getNodeType() != Node.ELEMENT_NODE && !node.getNodeName().equals(ImportPCFBatchConst.ELEMENT_TYPECODE_TYPE)) {
                continue;
            }
            var element = (Element) node;
            joiner.add(element.getAttribute(ImportPCFBatchConst.ELEMENT_TYPECODE_CODE));

        }
        return joiner;

    }

}
