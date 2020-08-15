package com.arrowsoft.pcftoqaautomation.batch.generatefiles.util;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Log4j2
@Component
public class GenerateQAFilesBatchUtil {

    private final EnumRepository enumRepository;

    public GenerateQAFilesBatchUtil(EnumRepository enumRepository) {
        this.enumRepository = enumRepository;
    }

    public GenerateEnumsTransport populateValueTransport(GenerateEnumsTransport generateEnumsTransport) {
        var project = generateEnumsTransport.getProject();
        var enumName = generateEnumsTransport.getEnumName();
        var enumEntities = enumRepository.findByProjectAndName(project, enumName);
        var values = new ArrayList<String>();
        for (EnumEntity enumEntity : enumEntities) {
            var value = enumEntity.getValue();
            if (value.isBlank()) {
                continue;

            }
            values.addAll(Arrays.asList(value.split(",")));

        }
        if (values.isEmpty()) {
            return null;

        }
        generateEnumsTransport.setValues(values);
        return generateEnumsTransport;

    }
}
