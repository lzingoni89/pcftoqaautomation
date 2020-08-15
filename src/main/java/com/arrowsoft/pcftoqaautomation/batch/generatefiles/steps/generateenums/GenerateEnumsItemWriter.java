package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.batch.shared.TemplateBatchUtil;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Log4j2
@Component
public class GenerateEnumsItemWriter implements ItemWriter<GenerateEnumsTransport> {

    private final TemplateBatchUtil templateBatchUtil;
    private final SharedBatchUtil sharedBatchUtil;
    private ProjectEntity project;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.project = sharedBatchUtil.getProject(stepExecution);

    }

    public GenerateEnumsItemWriter(TemplateBatchUtil templateBatchUtil,
                                   SharedBatchUtil sharedBatchUtil) {
        this.templateBatchUtil = templateBatchUtil;
        this.sharedBatchUtil = sharedBatchUtil;
    }

    @Override
    public void write(List<? extends GenerateEnumsTransport> list) throws IOException {
        this.templateBatchUtil.generateEnumFiles(project, list);

    }

}
