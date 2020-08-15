package com.arrowsoft.pcftoqaautomation.batch.generatefiles;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.JobCompletionNotificationListener;
import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.GenerateEnumsStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GenerateQAFilesBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final GenerateEnumsStepBuilder generateEnumsStepBuilder;

    public GenerateQAFilesBatch(JobBuilderFactory jobBuilderFactory,
                                GenerateEnumsStepBuilder generateEnumsStepBuilder) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.generateEnumsStepBuilder = generateEnumsStepBuilder;

    }

    @Bean
    public Job generateQAFilesJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory.get(BatchProcessCode.PCF_TO_NET_BATCH.getCode())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(generateEnumsStepBuilder.getStep())
                .end()
                .build();

    }

}
