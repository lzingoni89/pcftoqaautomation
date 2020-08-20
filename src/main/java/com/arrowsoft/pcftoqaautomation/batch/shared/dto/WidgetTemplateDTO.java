package com.arrowsoft.pcftoqaautomation.batch.shared.dto;

import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import lombok.Getter;

import java.util.List;
import java.util.StringJoiner;

@Getter
public class WidgetTemplateDTO {

    private final String renderID;
    private final String widgetPCFID;
    private final String refRenderID;
    private final String parentVarName;
    private final String enumRef;
    private final WidgetTypeEntity widgetType;

    public WidgetTemplateDTO(WidgetEntity widget,
                             String refRenderID,
                             List<WidgetTemplateDTO> widgetDTOs) {
        this.widgetPCFID = widget.getWidgetPCFID();
        this.widgetType = widget.getWidgetType();
        this.enumRef = widget.getEnumRef();
        this.refRenderID = refRenderID;
        var renderIDJoiner = new StringJoiner(this.widgetType.getRenderIDJoinerChar());
        renderIDJoiner.add(refRenderID);
        if (!widget.getRenderID().isBlank()) {
            renderIDJoiner.add(widget.getRenderID());

        }
        this.renderID = renderIDJoiner.toString();
        this.parentVarName = this.getParentVarNameFromDTOs(widgetDTOs);

    }

    private String getParentVarNameFromDTOs(List<WidgetTemplateDTO> widgetDTOs) {
        if (!this.renderID.contains(this.widgetType.getRenderIDJoinerChar())) {
            return "";

        }
        var parentRenderID = this.renderID.substring(0, this.renderID.lastIndexOf(this.widgetType.getRenderIDJoinerChar()));
        var parentDTO = widgetDTOs.stream().filter(dto -> dto.renderID.equals(parentRenderID)).findFirst().orElse(null);
        if (parentDTO == null) {
            return "";
        }
        return parentDTO.getWidgetType().getPrefix().getPrefix() + parentDTO.getWidgetPCFID();

    }

}
