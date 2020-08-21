package com.arrowsoft.pcftoqaautomation.batch;

import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.service.GitRepositoryService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final SharedBatchUtil sharedBatchUtil;
    private final GitRepositoryService gitRepositoryService;

    public JobCompletionNotificationListener(SharedBatchUtil sharedBatchUtil,
                                             GitRepositoryService gitRepositoryService) {
        this.sharedBatchUtil = sharedBatchUtil;
        this.gitRepositoryService = gitRepositoryService;
    }

    @SneakyThrows
    @Override
    public void beforeJob(JobExecution jobExecution) {
        var jobName = jobExecution.getJobInstance().getJobName();
        log.info(jobExecution.getJobInstance().getJobName() + " Started: " + jobExecution.getStartTime());
        var project = this.sharedBatchUtil.getProject(jobExecution.getJobParameters());
        switch (jobName) {
            case "IMPORT_PCF_BATCH":
                this.sharedBatchUtil.purgeTablesByProject(project);
                this.gitRepositoryService.pullRepository(project);
                break;

            case "PCF_TO_NET_BATCH":
                this.sharedBatchUtil.purgeFilesByProject(project);
                break;

        }

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(jobExecution.getJobInstance().getJobName() + " Finished: " + jobExecution.getEndTime());
        log.info(jobExecution.getJobInstance().getJobName() + " Status: " + jobExecution.getStatus().name());

    }

}
