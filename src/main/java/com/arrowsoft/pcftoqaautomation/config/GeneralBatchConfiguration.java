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
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

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
        var jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;

    }

    @Bean
    public TaskExecutor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(64);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(64);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }

}