package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum GWVersionEnum {

    VER_9("9.0"),
    VER_10("10.0");

    private final String desc;

    GWVersionEnum(String desc) {
        this.desc = desc;
    }

}
