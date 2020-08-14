package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum GWVersionEnum {

    VER_9("9.0", ":"),
    VER_10("10.0", "-");

    private final String desc;
    private final String renderIDCharJoiner;

    GWVersionEnum(String desc, String renderIDCharJoiner) {
        this.desc = desc;
        this.renderIDCharJoiner = renderIDCharJoiner;

    }

}
