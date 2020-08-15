package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

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

}
