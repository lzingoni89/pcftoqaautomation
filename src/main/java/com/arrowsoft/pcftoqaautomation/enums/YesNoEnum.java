package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum YesNoEnum {

    Yes(true),
    No(false);

    private final boolean originalValue;

    YesNoEnum(boolean originalValue) {
        this.originalValue = originalValue;

    }

}
