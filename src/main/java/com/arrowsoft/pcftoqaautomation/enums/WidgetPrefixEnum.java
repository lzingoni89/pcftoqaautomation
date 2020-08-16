package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum WidgetPrefixEnum {

    TXT("Txt"),
    CB("Cb"),
    CHK("Chk"),
    GRID("Grid"),
    TLT("Tlt"),
    LNK("Lnk"),
    MSG("Msg"),
    TAB("Tab"),
    LOC("Loc"),
    BTN("Btn");

    private final String prefix;

    WidgetPrefixEnum(String prefix) {
        this.prefix = prefix;
    }

}
