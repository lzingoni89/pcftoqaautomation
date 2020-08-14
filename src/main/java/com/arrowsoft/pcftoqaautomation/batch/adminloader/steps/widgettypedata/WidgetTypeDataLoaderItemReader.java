package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.widgettypedata;

import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

@Log4j2
@Component
public class WidgetTypeDataLoaderItemReader implements ItemReader<WidgetTypeEnum> {
    private Iterator<WidgetTypeEnum> widgetTypeList;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.widgetTypeList = Arrays.stream(WidgetTypeEnum.values()).iterator();

    }

    @Override
    public WidgetTypeEnum read() {
        return widgetTypeList.hasNext() ? widgetTypeList.next() : null;

    }
}
