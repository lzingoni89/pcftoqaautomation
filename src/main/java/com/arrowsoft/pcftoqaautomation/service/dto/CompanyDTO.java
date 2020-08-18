package com.arrowsoft.pcftoqaautomation.service.dto;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import lombok.Getter;

@Getter
public class CompanyDTO {

    private final Long id;
    private final String name;
    private final String codNamespace;

    public CompanyDTO(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        this.codNamespace = companyEntity.getCodNamespace();

    }

}
