package com.arrowsoft.pcftoqaautomation.batch;

import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.BatchProcessCodeDTO;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum BatchProcessCode {

    IMPORT_PCF_BATCH("IMPORT_PCF_BATCH", "Import PCFs and TypeCodes", "Import and Update PCFs and TypeCodes from GW to DB"),
    PCF_TO_NET_BATCH("PCF_TO_NET_BATCH", "Generate C# files from PCFs", "Generate C# files"),
    DATA_ADMIN_LOADER_BATCH("DATA_ADMIN_LOADER_BATCH", "Data Admin Loader", "Insert into DB admin data preloaded");

    private final String code;
    private final String name;
    private final String desc;

    private static final Set<BatchProcessCodeDTO> valuesDTO = new HashSet<>();
    private static final Set<String> pcfBatchCodes = new HashSet<>();

    BatchProcessCode(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;

    }

    public static Set<BatchProcessCodeDTO> getValuesDTO() {
        if (valuesDTO.isEmpty()) {
            for (BatchProcessCode value : BatchProcessCode.values()) {
                valuesDTO.add(new BatchProcessCodeDTO(value));
            }
        }
        return valuesDTO;

    }

    public static Set<String> getPcfBatchCodes() {
        if (pcfBatchCodes.isEmpty()) {
            pcfBatchCodes.add(IMPORT_PCF_BATCH.code);
            pcfBatchCodes.add(PCF_TO_NET_BATCH.code);

        }
        return pcfBatchCodes;
    }

}
