package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto;

import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class GenerateQAFilesTransport {

    private final String pcfName;
    private final String pcfPath;
    private List<WidgetEntity> widgets;

    public GenerateQAFilesTransport(Object[] pcf) {
        this.pcfName = pcf[0].toString();
        this.pcfPath = pcf[1].toString();

    }

    public void setWidgets(List<WidgetEntity> widgets) {
        this.widgets = widgets;

    }

}
