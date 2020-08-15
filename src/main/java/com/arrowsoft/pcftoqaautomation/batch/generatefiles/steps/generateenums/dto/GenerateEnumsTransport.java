package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GenerateEnumsTransport {

    private final ProjectEntity project;
    private final String enumName;
    private List<String> values;

    public GenerateEnumsTransport(ProjectEntity project, String enumName) {
        this.project = project;
        this.enumName = enumName;

    }

    public void setValues(List<String> values) {
        this.values = values.stream().sorted().collect(Collectors.toList());

    }

}
