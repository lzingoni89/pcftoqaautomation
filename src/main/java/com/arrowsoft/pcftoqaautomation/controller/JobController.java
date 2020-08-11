package com.arrowsoft.pcftoqaautomation.controller;

import com.arrowsoft.pcftoqaautomation.batch.dto.BatchProcessCodeDTO;
import com.arrowsoft.pcftoqaautomation.batch.dto.JobExecutionDTO;
import com.arrowsoft.pcftoqaautomation.service.JobLauncherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/webservice/batch")
public class JobController {

    private final JobLauncherService jobLauncherService;

    public JobController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;

    }

    @GetMapping
    public Set<BatchProcessCodeDTO> getBatchProcessCodeList() {
        return this.jobLauncherService.getBatchProcessCodeList();

    }

    @GetMapping("/{jobExecutionId}")
    public JobExecutionDTO getJobExecutionById(@PathVariable Long jobExecutionId) {
        return this.jobLauncherService.getJobExecutionById(jobExecutionId);

    }

    @PostMapping("/{batchCode}")
    public Long executeJobByCode(@PathVariable String batchCode) throws JobParametersInvalidException,
            JobInstanceAlreadyExistsException,
            NoSuchJobException,
            JobExecutionAlreadyRunningException {
        return jobLauncherService.executeJobByCode(batchCode);

    }

    @PutMapping("/{batchCode}")
    public void terminateJobExecutionById(@PathVariable String batchCode) throws JobExecutionNotRunningException,
            NoSuchJobExecutionException,
            NoSuchJobException {
        this.jobLauncherService.terminateJob(batchCode);

    }

    @DeleteMapping("/{batchCode}")
    public void killJob(@PathVariable String batchCode) throws NoSuchJobExecutionException,
            NoSuchJobException,
            JobExecutionAlreadyRunningException {
        this.jobLauncherService.killJob(batchCode);

    }

}
