package com.arrowsoft.pcftoqaautomation.batch.adminloader;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.JobCompletionNotificationListener;
import com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata.CompanyDataLoaderStepBuilder;
import com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.projectdata.ProjectDataLoaderStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AdminLoaderBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final CompanyDataLoaderStepBuilder companyDataLoaderStepBuilder;
    private final ProjectDataLoaderStepBuilder projectDataLoaderStepBuilder;

    public AdminLoaderBatch(JobBuilderFactory jobBuilderFactory,
                            CompanyDataLoaderStepBuilder companyDataLoaderStepBuilder,
                            ProjectDataLoaderStepBuilder projectDataLoaderStepBuilder) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.companyDataLoaderStepBuilder = companyDataLoaderStepBuilder;
        this.projectDataLoaderStepBuilder = projectDataLoaderStepBuilder;

    }

    @Bean
    public Job adminLoaderJob(JobCompletionNotificationListener listener) {

        return jobBuilderFactory.get(BatchProcessCode.DATA_ADMIN_LOADER_BATCH.getCode())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(companyDataLoaderStepBuilder.getStep())
                .next(projectDataLoaderStepBuilder.getStep())
                .end()
                .build();

    }

}
