package com.arrowsoft.pcftoqaautomation.service.dto.joblauncher;

import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchParameterCodes;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class JobExecutionStatusDTO {

    private Long id;
    private Long jobId;
    private String jobName;
    private String status;
    private Date startTime;
    private Date createTime;
    private Date endTime;
    private Date lastUpdated;
    private String exitStatusCode;
    private String exitStatusDesc;
    private String company;
    private String module;
    private String version;
    private Set<StepExecutionStatusDTO> steps;

    public JobExecutionStatusDTO(JobExecution jobExecution) {
        this.id = jobExecution.getId();
        var job = jobExecution.getJobInstance();
        this.jobId = job.getId();
        this.jobName = job.getJobName();
        this.status = jobExecution.getStatus().name();
        this.startTime = jobExecution.getStartTime();
        this.createTime = jobExecution.getCreateTime();
        this.endTime = jobExecution.getEndTime();
        this.lastUpdated = jobExecution.getLastUpdated();
        var exitStatus = jobExecution.getExitStatus();
        this.exitStatusCode = exitStatus.getExitCode();
        this.exitStatusDesc = exitStatus.getExitDescription();
        var parameters = jobExecution.getJobParameters();
        this.company = parameters.getString(SharedBatchParameterCodes.COMPANY_CODE);
        this.module = parameters.getString(SharedBatchParameterCodes.MODULE_CODE);
        this.version = parameters.getString(SharedBatchParameterCodes.VERSION_CODE);
        steps = new HashSet<>();
        for (StepExecution step : jobExecution.getStepExecutions()) {
            steps.add(new StepExecutionStatusDTO(step));

        }

    }

}
