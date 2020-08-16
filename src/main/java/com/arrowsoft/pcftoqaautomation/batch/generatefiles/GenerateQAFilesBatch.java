package com.arrowsoft.pcftoqaautomation.batch.generatefiles;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.JobCompletionNotificationListener;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.GenerateEnumsStepBuilder;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.GenerateQAFilesStepBuilder;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GenerateQAFilesBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final GenerateEnumsStepBuilder generateEnumsStepBuilder;
    private final GenerateQAFilesStepBuilder generateQAFilesStepBuilder;
    private final SharedBatchUtil sharedBatchUtil;

    public GenerateQAFilesBatch(JobBuilderFactory jobBuilderFactory,
                                GenerateEnumsStepBuilder generateEnumsStepBuilder,
                                GenerateQAFilesStepBuilder generateQAFilesStepBuilder,
                                SharedBatchUtil sharedBatchUtil) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.generateEnumsStepBuilder = generateEnumsStepBuilder;
        this.generateQAFilesStepBuilder = generateQAFilesStepBuilder;
        this.sharedBatchUtil = sharedBatchUtil;

    }

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) throws IOException {
        var project = this.sharedBatchUtil.getProject(jobExecution.getJobParameters());
        this.sharedBatchUtil.purgeFilesByProject(project);

    }

    @Bean
    public Job generateQAFilesJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory.get(BatchProcessCode.PCF_TO_NET_BATCH.getCode())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(generateEnumsStepBuilder.getStep())
                .next(generateQAFilesStepBuilder.getStep())
                .end()
                .build();

    }

}
