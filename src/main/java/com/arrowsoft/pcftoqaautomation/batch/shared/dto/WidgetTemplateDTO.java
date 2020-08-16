package com.arrowsoft.pcftoqaautomation.batch.shared.dto;

import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import lombok.Getter;

@Getter
public class WidgetTemplateDTO {

    private final String widgetPCFID;
    private final WidgetTypeEntity widgetType;
    private final String renderID;

    public WidgetTemplateDTO(WidgetEntity widget, String refRenderID) {
        this.widgetPCFID = widget.getWidgetPCFID();
        this.widgetType = widget.getWidgetType();
        this.renderID = refRenderID.isBlank() ? widget.getRenderID() : (refRenderID + widgetType.getRenderIDJoinerChar() + widget.getRenderID());

    }

}
