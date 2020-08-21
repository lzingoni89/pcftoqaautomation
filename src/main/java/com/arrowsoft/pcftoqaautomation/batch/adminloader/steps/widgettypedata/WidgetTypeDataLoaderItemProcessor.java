package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.widgettypedata;

import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class WidgetTypeDataLoaderItemProcessor implements ItemProcessor<WidgetTypeEnum, WidgetTypeEntity> {
    private final WidgetTypeRepository widgetTypeRepository;

    public WidgetTypeDataLoaderItemProcessor(WidgetTypeRepository widgetTypeRepository) {
        this.widgetTypeRepository = widgetTypeRepository;
    }

    @Override
    public WidgetTypeEntity process(WidgetTypeEnum widgetTypeEnum) {
        if (!this.widgetTypeRepository.existsWidgetTypeByTypeAndVersion(widgetTypeEnum, GWVersionEnum.VER_10)) {
            return new WidgetTypeEntity(widgetTypeEnum, GWVersionEnum.VER_10);
        }
        if (!this.widgetTypeRepository.existsWidgetTypeByTypeAndVersion(widgetTypeEnum, GWVersionEnum.VER_9)) {
            return new WidgetTypeEntity(widgetTypeEnum, GWVersionEnum.VER_9);
        }
        return null;

    }
}
