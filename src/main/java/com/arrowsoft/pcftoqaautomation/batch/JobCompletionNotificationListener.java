package com.arrowsoft.pcftoqaautomation.batch;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobInstance().getJobName() + " Started: " + jobExecution.getStartTime());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobInstance().getJobName() + " Finished: " + jobExecution.getEndTime());
        log.info(jobExecution.getJobInstance().getJobName() + " Status: " + jobExecution.getStatus().name());

    }

}
