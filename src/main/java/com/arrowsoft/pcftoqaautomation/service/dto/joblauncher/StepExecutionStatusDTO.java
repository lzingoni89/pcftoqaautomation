package com.arrowsoft.pcftoqaautomation.service.dto.joblauncher;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.StepExecution;

import java.util.Date;

@Data
@NoArgsConstructor
public class StepExecutionStatusDTO {

    private String stepName;
    private String status;
    private int readCount;
    private int writeCount;
    private int commitCount;
    private int rollbackCount;
    private int readSkipCount;
    private int processSkipCount;
    private int writeSkipCount;
    private Date startTime;
    private Date endTime;
    private Date lastUpdated;
    private String exitStatusCode;
    private String exitStatusDesc;
    private boolean terminateOnly;
    private int filterCount;

    public StepExecutionStatusDTO(StepExecution stepExecution) {
        this.stepName = stepExecution.getStepName();
        this.status = stepExecution.getStatus().name();
        this.readCount = stepExecution.getReadCount();
        this.writeCount = stepExecution.getWriteCount();
        this.commitCount = stepExecution.getCommitCount();
        this.rollbackCount = stepExecution.getRollbackCount();
        this.readSkipCount = stepExecution.getReadSkipCount();
        this.processSkipCount = stepExecution.getProcessSkipCount();
        this.writeSkipCount = stepExecution.getWriteSkipCount();
        this.startTime = stepExecution.getStartTime();
        this.endTime = stepExecution.getEndTime();
        this.lastUpdated = stepExecution.getLastUpdated();
        var exitStatus = stepExecution.getExitStatus();
        this.exitStatusCode = exitStatus.getExitCode();
        this.exitStatusDesc = exitStatus.getExitDescription();
        this.terminateOnly = stepExecution.isTerminateOnly();
        this.filterCount = stepExecution.getFilterCount();

    }

}