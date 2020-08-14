package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.pcftodb;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class PCFToDBItemWriter  implements ItemWriter<PCFEntity> {

    private final PCFRepository pcfRepository;
    private final WidgetRepository widgetRepository;

    public PCFToDBItemWriter(PCFRepository pcfRepository, WidgetRepository widgetRepository) {
        this.pcfRepository = pcfRepository;
        this.widgetRepository = widgetRepository;
    }

    @Override
    public void write(List<? extends PCFEntity> list) {

        for(PCFEntity pcf : list) {
            if(pcf.getWidgets().isEmpty()) {
                continue;

            }
            if(pcf.isNewPCF()) {
                pcf = pcfRepository.saveAndFlush(pcf);

            }
            widgetRepository.saveAll(pcf.getWidgets());

        }

    }
}
