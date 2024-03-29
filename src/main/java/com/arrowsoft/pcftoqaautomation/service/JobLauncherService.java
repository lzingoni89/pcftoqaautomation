package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchParameterCodes;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.BatchExecutionDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.BatchProcessCodeDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.JobExecutionStatusDTO;
import com.arrowsoft.pcftoqaautomation.service.validation.JobLauncherValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JobLauncherService {
    private final JobExplorer jobExplorer;
    private final SimpleJobOperator superAutoJobOperator;
    private final JobLauncherValidation jobLauncherValidation;

    public JobLauncherService(JobExplorer jobExplorer,
                              SimpleJobOperator superAutoJobOperator,
                              JobLauncherValidation jobLauncherValidation) {
        this.jobExplorer = jobExplorer;
        this.superAutoJobOperator = superAutoJobOperator;
        this.jobLauncherValidation = jobLauncherValidation;
    }

    public Set<BatchProcessCodeDTO> getBatchProcessCodeList() {
        return BatchProcessCode.getValuesDTO();

    }

    public Long executeJobByCode(BatchExecutionDTO batchExecutionDTO) throws JobParametersInvalidException,
            JobInstanceAlreadyExistsException,
            NoSuchJobException,
            JobExecutionAlreadyRunningException {

        jobLauncherValidation.validateBatchExecution(batchExecutionDTO);
        return superAutoJobOperator.start(batchExecutionDTO.getBatchProcessCode(), jobParametersToString(batchExecutionDTO));

    }

    private String jobParametersToString(BatchExecutionDTO batchExecutionDTO) {
        var parameters = new StringJoiner(",");
        if (batchExecutionDTO.getParameters() != null) {
            for (var param : batchExecutionDTO.getParameters().entrySet()) {
                parameters.add(param.getKey() + "=" + param.getValue());

            }
        }
        parameters.add("uniqueID=" + Date.from(Instant.now()));
        return parameters.toString();

    }

    public JobExecutionStatusDTO getJobExecutionById(Long id) {
        var jobExecution = jobExplorer.getJobExecution(id);
        if (jobExecution == null) {
            return null;
        }
        return new JobExecutionStatusDTO(jobExecution);

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

    public void terminateExecution(Long executionId) {
        try {
            superAutoJobOperator.stop(executionId);
            superAutoJobOperator.abandon(executionId);

        } catch (NoSuchJobExecutionException
                | JobExecutionNotRunningException
                | JobExecutionAlreadyRunningException e) {
            log.error(e.getMessage());
            log.error(e);

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

    public boolean isJobRunning(BatchExecutionDTO batchExecutionDTO) {
        var jobExecutions = this.jobExplorer
                .findRunningJobExecutions(batchExecutionDTO.getBatchProcessCode())
                .iterator();
        if (!jobExecutions.hasNext()) {
            return false;

        }
        var parameters = batchExecutionDTO.getParameters();
        if (parameters == null) {
            return true;

        }
        while (jobExecutions.hasNext()) {
            var jobParameters = jobExecutions.next().getJobParameters();
            if (Objects.equals(jobParameters.getString(SharedBatchParameterCodes.COMPANY_CODE), parameters.get(SharedBatchParameterCodes.COMPANY_CODE))
                    && Objects.equals(jobParameters.getString(SharedBatchParameterCodes.MODULE_CODE), parameters.get(SharedBatchParameterCodes.MODULE_CODE))
                    && Objects.equals(jobParameters.getString(SharedBatchParameterCodes.VERSION_CODE), parameters.get(SharedBatchParameterCodes.VERSION_CODE))) {
                return true;

            }

        }
        return false;

    }

    public Set<JobExecutionStatusDTO> getWorkingJobs() {
        var jobs = new HashSet<JobExecutionStatusDTO>();
        for (var batchCode : BatchProcessCode.values()) {
            var runningJobs = this.jobExplorer.findRunningJobExecutions(batchCode.getCode());
            if (runningJobs.isEmpty()) {
                continue;

            }
            jobs.addAll(runningJobs.stream().map(JobExecutionStatusDTO::new).collect(Collectors.toSet()));

        }
        return jobs;

    }

}
