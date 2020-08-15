package com.arrowsoft.pcftoqaautomation.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobsConfiguration {

    public JobsConfiguration(JobRegistry jobRegistry,
                             Job adminLoaderJob,
                             Job importPCFJob,
                             Job generateQAFilesJob) throws DuplicateJobException {
        jobRegistry.register(new ReferenceJobFactory(adminLoaderJob));
        jobRegistry.register(new ReferenceJobFactory(importPCFJob));
        jobRegistry.register(new ReferenceJobFactory(generateQAFilesJob));

    }

}
