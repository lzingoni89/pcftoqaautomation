package com.arrowsoft.pcftoqaautomation.batch;

import com.arrowsoft.pcftoqaautomation.batch.dto.BatchProcessCodeDTO;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum BatchProcessCode {

    DATA_ADMIN_LOADER_BATCH("DATA_ADMIN_LOADER_BATCH", "Load admin data", "Insert into DB admin data preloaded");

    private final String code;
    private final String name;
    private final String desc;

    BatchProcessCode(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;

    }

    public static Set<BatchProcessCodeDTO> getValuesDTO() {
        var set = new HashSet<BatchProcessCodeDTO>();
        for (BatchProcessCode value : BatchProcessCode.values()) {
            set.add(new BatchProcessCodeDTO(value));
        }
        return set;

    }

}
