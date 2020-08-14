package com.arrowsoft.pcftoqaautomation.batch.importpcf;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.JobCompletionNotificationListener;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.pcftodb.PCFToDBStepBuilder;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.typecodetodb.TypeCodeToDBStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ImportPCFBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final PCFToDBStepBuilder pcfToDBStepBuilder;
    private final TypeCodeToDBStepBuilder typeCodeToDBStepBuilder;

    public ImportPCFBatch(JobBuilderFactory jobBuilderFactory,
                          PCFToDBStepBuilder pcfToDBStepBuilder,
                          TypeCodeToDBStepBuilder typeCodeToDBStepBuilder) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.pcfToDBStepBuilder = pcfToDBStepBuilder;
        this.typeCodeToDBStepBuilder = typeCodeToDBStepBuilder;

    }

    @Bean
    public Job importPCFJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory.get(BatchProcessCode.IMPORT_PCF_BATCH.getCode())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(pcfToDBStepBuilder.getStep())
                .next(typeCodeToDBStepBuilder.getStep())
                .end()
                .build();

    }

}
