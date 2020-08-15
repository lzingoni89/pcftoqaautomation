package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.util.GenerateQAFilesBatchUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GenerateEnumsItemProcessor implements ItemProcessor<GenerateEnumsTransport, GenerateEnumsTransport> {

    private final GenerateQAFilesBatchUtil generateQAFilesBatchUtil;

    public GenerateEnumsItemProcessor(GenerateQAFilesBatchUtil generateQAFilesBatchUtil) {
        this.generateQAFilesBatchUtil = generateQAFilesBatchUtil;
    }

    @Override
    public GenerateEnumsTransport process(GenerateEnumsTransport generateEnumsTransport) {
        return this.generateQAFilesBatchUtil.populateValueTransport(generateEnumsTransport);

    }

}
