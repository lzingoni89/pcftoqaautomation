package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import com.arrowsoft.pcftoqaautomation.repository.ProjectRepository;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.ProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;

    public CompanyService(CompanyRepository companyRepository,
                          ProjectRepository projectRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }

    public List<CompanyDTO> getCompanyList() {
        return this.companyRepository.findAll().stream().map(CompanyDTO::new).collect(Collectors.toList());

    }

    public CompanyDTO findCompanyByID(String id) {
        var companyLongID = Long.parseLong(id);
        var companyDTO = this.companyRepository
                .findById(companyLongID)
                .map(CompanyDTO::new)
                .orElse(null);
        if (companyDTO == null) {
            return null;

        }
        companyDTO.setProjectList(this.projectRepository
                .findAllByCompany_Id(companyLongID)
                .stream().map(ProjectDTO::new)
                .collect(Collectors.toList()));
        return companyDTO;

    }

    public void updateCompany(CompanyDTO companyDTO) {
        var result = this.companyRepository.findById(companyDTO.getId());
        if (result.isEmpty()) {
            log.error("Company not found");
            return;

        }
        var company = result.get();
        company.setCodNamespace(companyDTO.getCodNamespace());
        company.setRetired(companyDTO.isRetired());
        this.companyRepository.saveAndFlush(company);

    }

    public void createOrUpdateProject(CompanyDTO companyDTO, ProjectDTO projectDTO) {
        ProjectEntity projectEntity = null;
        if (projectDTO.getId() != null) {
            projectEntity = this.projectRepository.findById(projectDTO.getId()).orElse(null);

        }
        if (projectEntity == null) {
            projectEntity = new ProjectEntity();
            projectEntity.setCompany(this.companyRepository.findById(companyDTO.getId()).orElse(null));

        }
        projectEntity.setModule(projectDTO.getModule());
        projectEntity.setVersion(projectDTO.getVersion());
        projectEntity.setRootPath(projectDTO.getRootPath());
        projectEntity.setAdminGitRepository(projectDTO.getAdminGitRepositoryView().isOriginalValue());
        this.projectRepository.saveAndFlush(projectEntity);

    }

}
