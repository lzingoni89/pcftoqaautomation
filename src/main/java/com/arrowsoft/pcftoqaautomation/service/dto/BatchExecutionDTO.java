package com.arrowsoft.pcftoqaautomation.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public final class BatchExecutionDTO {

    private String batchProcessCode;
    private Map<String, String> parameters;

}
