package com.arrowsoft.pcftoqaautomation.batch.shared;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.dto.WidgetTemplateDTO;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.repository.WidgetRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Log4j2
@Service
public class TemplateBatchUtil {

    private static final String FOLDER_TEMPLATE = "templates";

    private static final String FOLDER_ENUM = "Enums";
    private static final String ENUM_TEMPLATE = "Enum_Template.ftlh";

    private static final String ELEMENT_TEMPLATE = "Element_Template.ftlh";
    private static final String FOLDER_ELEMENT = "Elements";
    private static final String SUFIX_ELEMENT = "Element";

    private static final String PAGE_TEMPLATE = "Page_Template.ftlh";
    private static final String FOLDER_PAGE = "Pages";
    private static final String SUFIX_PAGE = "Page";

    private static final String CSHARP_EXTENSION = ".cs";

    private final WidgetRepository widgetRepository;
    private final WidgetTypeRepository widgetTypeRepository;
    private Configuration configuration;

    public TemplateBatchUtil(WidgetRepository widgetRepository,
                             WidgetTypeRepository widgetTypeRepository) {
        this.widgetRepository = widgetRepository;
        this.widgetTypeRepository = widgetTypeRepository;
        var classLoader = ClassLoader.getSystemClassLoader().getResource(FOLDER_TEMPLATE);
        if (classLoader == null) {
            log.info(SharedBatchMsg.ERROR_TEMPLATE_RESOURCE_NOT_FOUND);
            return;
        }
        this.configuration = new Configuration(Configuration.VERSION_2_3_29);
        try {
            this.configuration.setDirectoryForTemplateLoading(new File(classLoader.getFile()));
            this.configuration.setDefaultEncoding("UTF-8");
            this.configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            this.configuration.setLogTemplateExceptions(false);
            this.configuration.setWrapUncheckedExceptions(false);
            this.configuration.setFallbackOnNullLoopVariable(false);

        } catch (IOException e) {
            log.error(e);

        }

    }

    public void generateQAFiles(ProjectEntity project, List<? extends GenerateQAFilesTransport> list) throws IOException {
        var elementTemplate = this.configuration.getTemplate(ELEMENT_TEMPLATE);
        var pageTemplate = this.configuration.getTemplate(PAGE_TEMPLATE);
        var companyNamespace = project.getCompany().getCodNamespace();
        var moduleNamespace = project.getModule().getCodNamespace();
        var qaElementFolder = getQAElementPathFolder(project);
        var qaPageFolder = getQAPagePathFolder(project);
        var widgetTypesAllowed = this.widgetTypeRepository.findAllByVersionAndMigrate(project.getVersion(), true);
        for (GenerateQAFilesTransport transport : list) {
            var pcfName = transport.getPcfName();
            var pcfPath = transport.getPcfPath();
            var fileElementPath = getQAFilePath(qaElementFolder, pcfPath);
            var filePagePath = getQAFilePath(qaPageFolder, pcfPath);
            var root = new HashMap<>();
            root.put("companyNamespace", companyNamespace);
            root.put("moduleNamespace", moduleNamespace);
            root.put("pcfName", pcfName);
            var widgets = new ArrayList<WidgetTemplateDTO>();
            populateAllWidgets(widgetTypesAllowed, widgets, transport.getWidgets(), "");
            root.put("widgets", widgets);
            try (var outElement = createQAFile(fileElementPath, pcfName, SUFIX_ELEMENT);
                 var outPage = createQAFile(filePagePath, pcfName, SUFIX_PAGE)) {
                elementTemplate.process(root, outElement);
                pageTemplate.process(root, outPage);

            } catch (Exception e) {
                log.error(e);

            }

        }

    }

    public void generateEnumFiles(ProjectEntity project, List<? extends GenerateEnumsTransport> list) throws IOException {
        var temp = this.configuration.getTemplate(ENUM_TEMPLATE);
        var enumFolderPath = getEnumPathFolder(project);
        var companyNamespace = project.getCompany().getCodNamespace();
        var moduleNamespace = project.getModule().getCodNamespace();
        for (GenerateEnumsTransport transport : list) {
            var enumName = transport.getEnumName();
            try (OutputStreamWriter out = createEnumFile(enumFolderPath, enumName)) {
                var root = new HashMap<>();
                root.put("companyNamespace", companyNamespace);
                root.put("moduleNamespace", moduleNamespace);
                root.put("enumName", enumName);
                root.put("values", transport.getValues());
                temp.process(root, out);

            } catch (Exception e) {
                log.error(e);

            }

        }

    }

    private OutputStreamWriter createEnumFile(String enumFolderPath, String enumName) throws FileNotFoundException {
        var pathJoiner = new StringJoiner(File.separator);
        pathJoiner.add(enumFolderPath);
        pathJoiner.add(enumName + CSHARP_EXTENSION);
        return new OutputStreamWriter(new FileOutputStream(pathJoiner.toString()));

    }

    private String getQAFilePath(String qaElementFolderPath, String filePath) {
        var folderPath = qaElementFolderPath + filePath;
        var file = new File(folderPath);
        if (file.mkdirs() && log.isInfoEnabled()) {
            log.info("New folder created: " + folderPath);

        }
        return folderPath;

    }

    private OutputStreamWriter createQAFile(String folderPath, String enumName, String sufix) throws FileNotFoundException {
        var pathJoiner = new StringJoiner(File.separator);
        pathJoiner.add(folderPath);
        pathJoiner.add(enumName + sufix + CSHARP_EXTENSION);
        return new OutputStreamWriter(new FileOutputStream(pathJoiner.toString()));

    }

    private String getEnumPathFolder(ProjectEntity project) {
        var enumPathFolder = getProjectRootFolderPath(project) + File.separator + FOLDER_ENUM;
        var file = new File(enumPathFolder);
        if (file.mkdirs() && log.isInfoEnabled()) {
            log.info("New folder created: " + file.getPath());

        }
        return enumPathFolder;

    }

    private String getQAElementPathFolder(ProjectEntity project) {
        return getProjectRootFolderPath(project) + File.separator + FOLDER_ELEMENT;

    }

    private String getQAPagePathFolder(ProjectEntity project) {
        return getProjectRootFolderPath(project) + File.separator + FOLDER_PAGE;

    }

    private String getProjectRootFolderPath(ProjectEntity project) {
        var rootFolder = "C:/Users/Usuario"; //TODO tomar valor del sistema
        var joiner = new StringJoiner(File.separator);
        joiner.add(rootFolder);
        joiner.add(project.getCompany().getCompanyCodIntern().getName());
        joiner.add(project.getModule().getCodNamespace() + "_" + project.getVersion().getCode());
        return joiner.toString();

    }

    private void populateAllWidgets(Set<WidgetTypeEntity> widgetTypesAllowed,
                                    List<WidgetTemplateDTO> widgetDTOs,
                                    List<WidgetEntity> widgets, String refRenderID) {
        var widgetIDs = new HashSet<String>(widgets.size());
        for (WidgetEntity widgetEntity : widgets) {
            var widgetID = widgetEntity.getWidgetPCFID();
            if (widgetIDs.contains(widgetID)) {
                continue;

            }
            var dto = new WidgetTemplateDTO(widgetEntity, refRenderID, widgetDTOs);
            if (!dto.getWidgetPCFID().isBlank() && widgetTypesAllowed.contains(widgetEntity.getWidgetType())) {
                widgetIDs.add(widgetID);
                widgetDTOs.add(dto);
            }
            var pcfRef = widgetEntity.getPcfRef();
            if (pcfRef.isBlank()) {
                continue;

            }
            var refWidgets = this.widgetRepository.findByPcfNameOrderByWidgetPCFID(pcfRef);
            if (refWidgets != null && !refWidgets.isEmpty()) {
                populateAllWidgets(widgetTypesAllowed, widgetDTOs, refWidgets, dto.getRenderID());

            }

        }

    }

}
