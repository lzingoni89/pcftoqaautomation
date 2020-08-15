package com.arrowsoft.pcftoqaautomation.batch.shared;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import com.arrowsoft.pcftoqaautomation.repository.ProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SharedBatchUtil {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;

    public SharedBatchUtil(CompanyRepository companyRepository,
                           ProjectRepository projectRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;

    }

    public ProjectEntity getProject(StepExecution stepExecution) {
        var parameters = stepExecution.getJobParameters();
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

}
