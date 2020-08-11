package com.arrowsoft.pcftoqaautomation.batch.adminloader;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.JobCompletionNotificationListener;
import com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata.CompanyDataLoaderStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class AdminLoaderBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final CompanyDataLoaderStepBuilder companyDataLoaderStepBuilder;

    public AdminLoaderBatch(JobBuilderFactory jobBuilderFactory, CompanyDataLoaderStepBuilder companyDataLoaderStepBuilder) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.companyDataLoaderStepBuilder = companyDataLoaderStepBuilder;

    }

    @Bean
    public Job importInfoAutoJob(JobCompletionNotificationListener listener) {
        var mainFlow = new FlowBuilder<Flow>("ParallelAdminLoaderFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(companyDataLoaderStepBuilder.getStep())
                .build();

        return jobBuilderFactory.get(BatchProcessCode.DATA_ADMIN_LOADER_BATCH.getCode())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(mainFlow)
                .end()
                .build();

    }

}
