package com.arrowsoft.pcftoqaautomation.batch.dto;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatchProcessCodeDTO {

    private String code;
    private String name;
    private String desc;

    public BatchProcessCodeDTO(BatchProcessCode batchProcessCode) {
        this.code = batchProcessCode.getCode();
        this.name = batchProcessCode.getName();
        this.desc = batchProcessCode.getDesc();

    }

}