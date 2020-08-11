package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum WidgetHowFindEnum {

    ID("Id"),
    NAME("Name");

    private final String how;

    WidgetHowFindEnum(String how) {
        this.how = how;
    }

}
