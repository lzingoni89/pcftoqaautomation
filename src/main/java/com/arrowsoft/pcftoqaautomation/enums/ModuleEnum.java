package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ModuleEnum {

    CC("Claim Center", "ClaimCenter"),
    BC("Billing Center", "BillingCenter"),
    PC("Policy Center", "PolicyCenter"),
    AB("Contact Manager", "ContactManager");

    private final String desc;
    private final String codNamespace;

    ModuleEnum(String desc, String codNamespace) {
        this.desc = desc;
        this.codNamespace = codNamespace;

    }

    public static ModuleEnum getByDesc(String desc) {
        return Arrays.stream(values()).filter(moduleEnum -> moduleEnum.desc.equals(desc)).findFirst().orElse(null);

    }

}
