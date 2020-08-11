package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum ModuleEnum {

    CC("Claim Center"),
    BC("Billing Center"),
    PC("Policy Center"),
    AB("Contact Manager");

    private final String desc;

    ModuleEnum(String desc) {
        this.desc = desc;
    }

}
