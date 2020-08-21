package com.arrowsoft.pcftoqaautomation.batch.shared;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import com.arrowsoft.pcftoqaautomation.repository.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

@Log4j2
@Component
public class SharedBatchUtil {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final WidgetRepository widgetRepository;
    private final PCFRepository pcfRepository;
    private final EnumRepository enumRepository;

    public SharedBatchUtil(CompanyRepository companyRepository,
                           ProjectRepository projectRepository,
                           WidgetRepository widgetRepository,
                           PCFRepository pcfRepository,
                           EnumRepository enumRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
        this.widgetRepository = widgetRepository;
        this.pcfRepository = pcfRepository;
        this.enumRepository = enumRepository;
    }

    public ProjectEntity getProject(JobParameters parameters) {
        if (log.isDebugEnabled()) {
            log.debug("---Parameters---");
            log.debug("Company: " + parameters.getString(SharedBatchParameterCodes.COMPANY_CODE));
            log.debug("Module: " + parameters.getString(SharedBatchParameterCodes.MODULE_CODE));
            log.debug("Version: " + parameters.getString(SharedBatchParameterCodes.VERSION_CODE));

        }
        var companyCode = CompanyEnum.valueOf(parameters.getString(SharedBatchParameterCodes.COMPANY_CODE));
        var company = companyRepository.findFirstByCompanyCodIntern(companyCode);
        if (company == null) {
            log.error(SharedBatchMsg.ERROR_COMPANY_NOT_FOUND);
            return null;

        }
        var module = ModuleEnum.valueOf(parameters.getString(SharedBatchParameterCodes.MODULE_CODE));
        var version = GWVersionEnum.valueOf(parameters.getString(SharedBatchParameterCodes.VERSION_CODE));
        return projectRepository.findFirstByCompanyAndModuleAndVersion(company, module, version);

    }

    @Transactional
    public void purgeTablesByProject(ProjectEntity projectEntity) {
        log.info("Cleaning Tables...");
        this.enumRepository.deleteAllByProjectAndEditable(projectEntity, false);
        this.widgetRepository.deleteAllByProject(projectEntity);
        this.pcfRepository.deleteAllByProject(projectEntity);

    }

    public void purgeFilesByProject(ProjectEntity projectEntity) throws IOException {
        log.info("Cleaning CSharp Files...");
        var rootFolderPath = "C:/Users/Usuario"; //TODO tomar valor del sistema
        var joiner = new StringJoiner(File.separator);
        joiner.add(rootFolderPath);
        joiner.add(projectEntity.getCompany().getCompanyCodIntern().getName());
        joiner.add(projectEntity.getModule().getCodNamespace() + "_" + projectEntity.getVersion().getCode());
        var rootFolder = new File(joiner.toString());
        if (!rootFolder.exists()) {
            return;
        }
        FileUtils.deleteDirectory(rootFolder);

    }

}
