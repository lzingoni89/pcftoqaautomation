package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.projectdata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ProjectDataLoaderStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final ProjectDataLoaderItemReader projectDataLoaderItemReader;
    private final ProjectDataLoaderItemProcessor projectDataLoaderItemProcessor;
    private final ProjectDataLoaderItemWriter projectDataLoaderItemWriter;

    public ProjectDataLoaderStepBuilder(StepBuilderFactory stepBuilderFactory,
                                        ProjectDataLoaderItemReader projectDataLoaderItemReader,
                                        ProjectDataLoaderItemProcessor projectDataLoaderItemProcessor,
                                        ProjectDataLoaderItemWriter projectDataLoaderItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.projectDataLoaderItemReader = projectDataLoaderItemReader;
        this.projectDataLoaderItemProcessor = projectDataLoaderItemProcessor;
        this.projectDataLoaderItemWriter = projectDataLoaderItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get("ProjectDataLoaderStep")
                .<CompanyEntity, Set<ProjectEntity>>chunk(4096)
                .reader(projectDataLoaderItemReader)
                .processor(projectDataLoaderItemProcessor)
                .writer(projectDataLoaderItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobProjectDataLoaderStep").start(step).build();

    }

}