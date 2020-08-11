package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum CompanyEnum {

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
