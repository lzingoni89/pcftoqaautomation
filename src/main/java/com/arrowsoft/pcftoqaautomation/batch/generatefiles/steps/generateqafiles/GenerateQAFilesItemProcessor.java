package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.util.GenerateQAFilesBatchUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GenerateQAFilesItemProcessor implements ItemProcessor<GenerateQAFilesTransport, GenerateQAFilesTransport> {

    private final GenerateQAFilesBatchUtil generateQAFilesBatchUtil;

    public GenerateQAFilesItemProcessor(GenerateQAFilesBatchUtil generateQAFilesBatchUtil) {
        this.generateQAFilesBatchUtil = generateQAFilesBatchUtil;
    }

    @Override
    public GenerateQAFilesTransport process(GenerateQAFilesTransport generateQAFilesTransport) {
        return generateQAFilesBatchUtil.populateWidgetsTransport(generateQAFilesTransport);

    }

}
