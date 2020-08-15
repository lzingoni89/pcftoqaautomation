package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class EnumTemplateDTO {

    private final String companyNamespace;
    private final String moduleNamespace;
    private final String enumName;
    private final List<String> values;

    public EnumTemplateDTO(String companyNamespace,
                           String moduleNamespace,
                           String enumName,
                           List<String> values) {
        this.companyNamespace = companyNamespace;
        this.moduleNamespace = moduleNamespace;
        this.enumName = enumName;
        this.values = values;

    }
}
