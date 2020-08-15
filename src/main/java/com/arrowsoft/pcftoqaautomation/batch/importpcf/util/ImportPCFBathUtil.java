package com.arrowsoft.pcftoqaautomation.batch.importpcf.util;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchMsg;
import com.arrowsoft.pcftoqaautomation.entity.*;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
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

    private final WidgetTypeRepository widgetTypeRepository;
    private final PCFRepository pcfRepository;
    private final WidgetRepository widgetRepository;
    private final EnumRepository enumRepository;

    public ImportPCFBathUtil(WidgetTypeRepository widgetTypeRepository,
                             PCFRepository pcfRepository,
                             WidgetRepository widgetRepository,
                             EnumRepository enumRepository) {
        this.widgetTypeRepository = widgetTypeRepository;
        this.pcfRepository = pcfRepository;
        this.widgetRepository = widgetRepository;
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

    public PCFEntity createPCFEntity(ImportPCFBatchTransport importPcfBatchTransport) throws IOException, SAXException, ParserConfigurationException {
        var file = importPcfBatchTransport.getFile();
        var project = importPcfBatchTransport.getProject();
        var pcf = pcfRepository.findFirstByProjectAndPcfFileName(project, file.getName());
        if (pcf == null) {
            pcf = new PCFEntity(project, getPCFFileName(file), getRelativeFilePath(file), file.getName());

        }
        var widgetTypeSet = widgetTypeRepository.findAllByVersionAndMigrate(project.getVersion(), true);
        var containerElement = getContainerElementFromFile(file);
        if (containerElement == null) {
            log.info(SharedBatchMsg.ERROR_CONTAINER_ELEMENT_NOT_FOUND);
            return null;
        }
        var widget = createWidget(pcf, widgetTypeSet, containerElement, null);
        if (widget == null) {
            return null;

        }
        readNodeChildren(pcf, widgetTypeSet, containerElement, widget);
        return pcf;

    }

    public EnumEntity createEnumEntity(ImportPCFBatchTransport importPcfBatchTransport) throws ParserConfigurationException, SAXException, IOException {
        var file = importPcfBatchTransport.getFile();
        var project = importPcfBatchTransport.getProject();
        var enumEntity = enumRepository.findFirstByProjectAndFileNameAndEditable(project, file.getName(), false);
        var value = getValuesFromTypeCodeFile(file);
        if (enumEntity == null) {
            enumEntity = new EnumEntity(project, getTypeCodeFileName(file), file.getName(), value);

        } else {
            enumEntity.setNewValues(value);

        }

        if (!enumEntity.isNewOrUpdated()) {
            return null;

        }
        return enumEntity;

    }

    private String getPCFFileName(File file) {
        var extensionIndex = file.getName().indexOf(".pcf");
        return file.getName().substring(0, extensionIndex);

    }

    private String getTypeCodeFileName(File file) {
        var extensionIndex = file.getName().indexOf(".");
        return file.getName().substring(0, extensionIndex);

    }

    private String getRelativeFilePath(File file) {
        var folderPath = file.getParent();
        return folderPath.substring(folderPath.indexOf("\\pcf") + 4);

    }

    private WidgetEntity createWidget(PCFEntity pcf, Set<WidgetTypeEntity> widgetTypeSet, Element element, WidgetEntity parent) {
        var widgetPCFID = element.getAttribute("id");
        if (widgetPCFID == null || widgetPCFID.isBlank()) {
            return null;
        }
        var widgetType = getWidgetTypeEntity(widgetTypeSet, WidgetTypeEnum.valueOf(element.getTagName()));
        if (widgetType == null) {
            return null;
        }
        WidgetEntity widgetEntity = null;
        if (!pcf.isNewPCF()) {
            widgetEntity = widgetRepository.findFirstByPcfAndWidgetTypeAndWidgetPCFID(pcf, widgetType, widgetPCFID);

        }
        if (widgetEntity == null) {
            widgetEntity = new WidgetEntity(pcf, widgetType, element, parent);

        } else if (parent != null && widgetEntity.getParent() != null && !widgetEntity.getParent().equals(parent)) {
            widgetEntity.setParent(parent);
            widgetEntity.setNewOrUpdated(true);

        }
        if (widgetEntity.isNewOrUpdated()) {
            pcf.getWidgets().add(widgetEntity);

        }
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
