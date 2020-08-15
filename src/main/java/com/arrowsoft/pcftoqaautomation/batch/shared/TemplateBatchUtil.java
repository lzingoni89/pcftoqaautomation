package com.arrowsoft.pcftoqaautomation.batch.shared;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.EnumTemplateDTO;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

@Service
@Log4j2
public class TemplateBatchUtil {

    private static final String FOLDER_TEMPLATE = "templates";
    private static final String FOLDER_ENUM = "Enums";
    private static final String ENUM_TEMPLATE = "Enum_Template.ftlh";
    private static final String ELEMENT_TEMPLATE = "Element_Template.ftlh";
    private static final String FOLDER_ELEMENT = "Elements";
    private static final String PAGE_TEMPLATE = "Page_Template.ftlh";
    private static final String FOLDER_PAGE = "Pages";
    private static final String CSHARP_EXTENSION = ".cs";

    private final PCFRepository pcfRepository;
    private Configuration configuration;

    public TemplateBatchUtil(PCFRepository pcfRepository) {
        this.pcfRepository = pcfRepository;
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

    public void useTemplate() throws IOException {
        var temp = this.configuration.getTemplate("test.ftlh");
        var root = new HashMap<>();
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("sample/result/TabBarElements.cs"))) {
            var pcfResult = pcfRepository.findById(20L);
            if (pcfResult.isEmpty()) {
                log.info("PCF NOT FOUND");
                return;

            }
            var pcf = pcfResult.get();
            root.put("pcf", pcf.getPcfName().substring(0, pcf.getPcfName().indexOf(".pcf")));
            temp.process(root, out);

        } catch (Exception e) {
            log.error(e);

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
                root.put("enum", new EnumTemplateDTO(companyNamespace, moduleNamespace, enumName, transport.getValues()));
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

    private String getEnumPathFolder(ProjectEntity project) {
        var enumPathFolder = getProjectRootFolderPath(project) + File.separator + FOLDER_ENUM;
        var file = new File(enumPathFolder);
        if (log.isInfoEnabled() && file.mkdirs()) {
            log.info("New folder created: " + file.getPath());

        }
        return enumPathFolder;

    }

    private String getProjectRootFolderPath(ProjectEntity project) {
        var rootFolder = "C:/Users/Usuario"; //TODO tomar valor del sistema
        var joiner = new StringJoiner(File.separator);
        joiner.add(rootFolder);
        joiner.add(project.getCompany().getCompanyCodIntern().getName());
        joiner.add(project.getModule().getCodNamespace() + "_" + project.getVersion().getCode());
        return joiner.toString();

    }

}
