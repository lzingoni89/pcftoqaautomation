package com.arrowsoft.pcftoqaautomation.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class GeneralBatchConfiguration {

    private final JobRegistry jobRegistry;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    public GeneralBatchConfiguration(JobRegistry jobRegistry,
                                     JobExplorer jobExplorer,
                                     JobRepository jobRepository) {
        this.jobRegistry = jobRegistry;
        this.jobExplorer = jobExplorer;
        this.jobRepository = jobRepository;
    }

    @Bean
    public SimpleJobOperator superAutoJobOperator() throws Exception {
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobRegistry(jobRegistry);
        operator.setJobExplorer(jobExplorer);
        operator.setJobRepository(jobRepository);
        operator.setJobLauncher(simpleJobLauncher());
        return operator;

    }

    @Bean
    public JobLauncher simpleJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;

    }

}