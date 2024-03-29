package com.arrowsoft.pcftoqaautomation.batch.generatefiles.util;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import com.arrowsoft.pcftoqaautomation.repository.WidgetRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Log4j2
@Component
public class GenerateQAFilesBatchUtil {

    private final EnumRepository enumRepository;
    private final WidgetRepository widgetRepository;

    public GenerateQAFilesBatchUtil(EnumRepository enumRepository,
                                    WidgetRepository widgetRepository) {
        this.enumRepository = enumRepository;
        this.widgetRepository = widgetRepository;
    }

    public GenerateEnumsTransport populateValueTransport(GenerateEnumsTransport transport) {
        var project = transport.getProject();
        var enumName = transport.getEnumName();
        var enumEntities = enumRepository.findByProjectAndName(project, enumName);
        var values = new ArrayList<String>();
        for (EnumEntity enumEntity : enumEntities) {
            values.addAll(Arrays.asList(enumEntity.getValue().split(",")));

        }
        transport.setValues(values);
        return transport;

    }

    public GenerateQAFilesTransport populateWidgetsTransport(GenerateQAFilesTransport transport) {
        transport.setWidgets(this.widgetRepository.findByPcfNameAndPcfPathOrderById(transport.getPcfName(), transport.getPcfPath()));
        return transport;

    }

}
