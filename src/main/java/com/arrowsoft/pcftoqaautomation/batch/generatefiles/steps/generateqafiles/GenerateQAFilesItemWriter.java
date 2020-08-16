package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
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
public class GenerateQAFilesItemWriter implements ItemWriter<GenerateQAFilesTransport> {

    private final TemplateBatchUtil templateBatchUtil;
    private final SharedBatchUtil sharedBatchUtil;
    private ProjectEntity project;

    public GenerateQAFilesItemWriter(TemplateBatchUtil templateBatchUtil, SharedBatchUtil sharedBatchUtil) {
        this.templateBatchUtil = templateBatchUtil;
        this.sharedBatchUtil = sharedBatchUtil;

    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.project = sharedBatchUtil.getProject(stepExecution.getJobParameters());

    }

    @Override
    public void write(List<? extends GenerateQAFilesTransport> list) throws IOException {
        this.templateBatchUtil.generateQAFiles(project, list);

    }


}
