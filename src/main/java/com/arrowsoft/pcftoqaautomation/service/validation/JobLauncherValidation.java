package com.arrowsoft.pcftoqaautomation.service.validation;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchParameterCodes;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.BatchExecutionDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class JobLauncherValidation {

    private final JobExplorer jobExplorer;

    public JobLauncherValidation(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    public void validateBatchExecution(BatchExecutionDTO batchExecutionDTO) throws NoSuchJobException, JobExecutionAlreadyRunningException {
        var batchProcessCode = batchExecutionDTO.getBatchProcessCode();
        if (batchProcessCode == null || batchProcessCode.isBlank()) {
            throw new NoSuchJobException("Code is empty");

        }
        var jobsRunning = jobExplorer.findRunningJobExecutions(batchProcessCode);
        if (!jobsRunning.isEmpty() && isPcfBatchRunning(jobsRunning, batchExecutionDTO)) {
            throw new JobExecutionAlreadyRunningException("Job execution already running");

        }

    }

    public boolean isPcfBatchRunning(Set<JobExecution> jobsRunning, BatchExecutionDTO batchExecutionDTO) {
        if (!BatchProcessCode.getPcfBatchCodes().contains(batchExecutionDTO.getBatchProcessCode())) {
            return false;
        }
        var parameters = batchExecutionDTO.getParameters();
        if (parameters == null || parameters.isEmpty()) {
            return true;

        }
        var company = parameters.get(SharedBatchParameterCodes.COMPANY_CODE);
        var module = parameters.get(SharedBatchParameterCodes.MODULE_CODE);
        var version = parameters.get(SharedBatchParameterCodes.VERSION_CODE);
        if (company == null || company.isBlank()
                || module == null || module.isBlank()
                || version == null || version.isBlank()) {
            return true;

        }

        for (JobExecution job : jobsRunning) {
            var jobParameters = job.getJobParameters();
            if (Objects.equals(jobParameters.getString(SharedBatchParameterCodes.COMPANY_CODE), company)
                || Objects.equals(jobParameters.getString(SharedBatchParameterCodes.MODULE_CODE), module)
                || Objects.equals(jobParameters.getString(SharedBatchParameterCodes.VERSION_CODE), version)) {
                return true;

            }

        }
        return false;

    }

}
