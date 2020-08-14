package com.arrowsoft.pcftoqaautomation.enums;

import lombok.Getter;

@Getter
public enum WidgetHowFindEnum {

    ID("Id"),
    NAME("Name"),
    TAG_NAME("TagName"),
    CLASS_NAME("ClassName"),
    CSS_SELECTOR("CssSelector"),
    LINK_TEXT("LinkText"),
    PARTIAL_LINK_TEXT("PartialLinkText"),
    XPATH("XPath"),
    CUSTOM("Custom");

    private final String how;

    WidgetHowFindEnum(String how) {
        this.how = how;
    }

}
