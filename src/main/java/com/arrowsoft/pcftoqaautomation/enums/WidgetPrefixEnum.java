package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum WidgetPrefixEnum {

    TXT("Txt"),
    BTN("Btn");

    private final String prefix;

    WidgetPrefixEnum(String prefix) {
        this.prefix = prefix;
    }

}
