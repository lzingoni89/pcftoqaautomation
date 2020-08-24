package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GWVersionEnum {

    VER_10("VER_10", "10.0", "-"),
    VER_9("VER_9", "9.0", ":");

    private final String code;
    private final String desc;
    private final String renderIDCharJoiner;

    GWVersionEnum(String code, String desc, String renderIDCharJoiner) {
        this.code = code;
        this.desc = desc;
        this.renderIDCharJoiner = renderIDCharJoiner;

    }

    public static GWVersionEnum getByDesc(String desc) {
        return Arrays.stream(values()).filter(versionEnum -> versionEnum.desc.equals(desc)).findFirst().orElse(null);

    }

}
