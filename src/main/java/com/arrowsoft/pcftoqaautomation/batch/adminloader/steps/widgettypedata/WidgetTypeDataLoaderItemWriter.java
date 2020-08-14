package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.widgettypedata;

import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class WidgetTypeDataLoaderItemWriter implements ItemWriter<WidgetTypeEntity> {

    private final WidgetTypeRepository widgetTypeRepository;

    public WidgetTypeDataLoaderItemWriter(WidgetTypeRepository widgetTypeRepository) {
        this.widgetTypeRepository = widgetTypeRepository;
    }

    @Override
    public void write(List<? extends WidgetTypeEntity> list) {
        this.widgetTypeRepository.saveAll(list);

    }
}
