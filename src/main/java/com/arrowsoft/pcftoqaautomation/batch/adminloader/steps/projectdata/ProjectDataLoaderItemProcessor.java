package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.projectdata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import com.arrowsoft.pcftoqaautomation.repository.ProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Component
public class ProjectDataLoaderItemProcessor implements ItemProcessor<CompanyEntity, Set<ProjectEntity>> {

    private final ProjectRepository projectRepository;

    public ProjectDataLoaderItemProcessor(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Set<ProjectEntity> process(CompanyEntity companyEntity) {
        var projects = new HashSet<ProjectEntity>();
        for (ModuleEnum module : ModuleEnum.values()) {
            if (projectRepository.existsProjectByCompanyAndModule(companyEntity, module)) {
                continue;

            }
            projects.add(new ProjectEntity(companyEntity, module, GWVersionEnum.VER_10));
            if (companyEntity.getCompanyCodIntern() == CompanyEnum.SANDBOX) {
                projects.add(new ProjectEntity(companyEntity, module, GWVersionEnum.VER_9));

            }

        }

        if (projects.isEmpty()) {
            return null;

        }

        return projects;

    }

}
