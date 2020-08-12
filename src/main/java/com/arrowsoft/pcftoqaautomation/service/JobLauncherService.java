package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.dto.BatchProcessCodeDTO;
import com.arrowsoft.pcftoqaautomation.batch.dto.JobExecutionDTO;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
@Service
public class JobLauncherService {
    private final JobExplorer jobExplorer;
    private final SimpleJobOperator superAutoJobOperator;

    public JobLauncherService(JobExplorer jobExplorer,
                              SimpleJobOperator superAutoJobOperator) {
        this.jobExplorer = jobExplorer;
        this.superAutoJobOperator = superAutoJobOperator;

    }

    public Set<BatchProcessCodeDTO> getBatchProcessCodeList() {
        return BatchProcessCode.getValuesDTO();

    }

    public Long executeJobByCode(String batchProcessCode) throws JobParametersInvalidException,
            JobInstanceAlreadyExistsException,
            NoSuchJobException,
            JobExecutionAlreadyRunningException {
        if (batchProcessCode == null || batchProcessCode.isBlank()) {
            throw new NoSuchJobException("Code is empty");

        }
        if (!jobExplorer.findRunningJobExecutions(batchProcessCode).isEmpty()) {
            throw new JobExecutionAlreadyRunningException("Job execution already running");

        }
        var jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        return superAutoJobOperator.start(batchProcessCode, jobParameters.toString());

    }

    public JobExecutionDTO getJobExecutionById(Long id) {
        var jobExecution = jobExplorer.getJobExecution(id);
        if (jobExecution == null) {
            return null;
        }
        return new JobExecutionDTO(jobExecution);

    }

    public void terminateJob(String batchProcessCode) throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        if (batchProcessCode == null || batchProcessCode.isBlank()) {
            return;
        }
        var executions = superAutoJobOperator.getRunningExecutions(batchProcessCode);
        if (executions.isEmpty()) {
            return;
        }
        for (Long executionId : executions) {
            superAutoJobOperator.stop(executionId);

        }

    }

    public void killJob(String batchProcessCode) throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
        if (batchProcessCode == null || batchProcessCode.isBlank()) {
            return;
        }
        var executions = superAutoJobOperator.getRunningExecutions(batchProcessCode);
        if (executions.isEmpty()) {
            return;
        }
        for (Long executionId : executions) {
            superAutoJobOperator.abandon(executionId);

        }

    }

}
