package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum CompanyEnum {

    SANDBOX_10("SB10", "SandBox 10"),
    SANDBOX_9("SB9", "SandBox 9"),
    SURA("SURA", "Sura"),
    SANCOR("SANCOR", "Sancor"),
    SAN_CRISTOBAL("SC", "San Cristobal"),
    LA_SEGUNDA("L2", "La Segunda");

    private final String codNamespace;
    private final String name;

    CompanyEnum(String codNamespace, String name) {
        this.codNamespace = codNamespace;
        this.name = name;

    }

}
