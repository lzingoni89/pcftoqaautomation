package com.arrowsoft.pcftoqaautomation.service.dto.company;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String codNamespace;
    private boolean retired;
    private List<ProjectDTO> projectList;

    public CompanyDTO(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        this.codNamespace = companyEntity.getCodNamespace();
        this.retired = companyEntity.isRetired();

    }

}
