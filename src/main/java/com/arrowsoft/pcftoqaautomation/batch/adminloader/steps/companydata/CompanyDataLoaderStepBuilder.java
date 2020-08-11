package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyDataLoaderStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final CompanyDataLoaderItemReader companyDataLoaderItemReader;
    private final CompanyDataLoaderItemProcessor companyDataLoaderItemProcessor;
    private final CompanyDataLoaderItemWriter companyDataLoaderItemWriter;

    public CompanyDataLoaderStepBuilder(StepBuilderFactory stepBuilderFactory,
                                        CompanyDataLoaderItemReader companyDataLoaderItemReader,
                                        CompanyDataLoaderItemProcessor companyDataLoaderItemProcessor,
                                        CompanyDataLoaderItemWriter companyDataLoaderItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.companyDataLoaderItemReader = companyDataLoaderItemReader;
        this.companyDataLoaderItemProcessor = companyDataLoaderItemProcessor;
        this.companyDataLoaderItemWriter = companyDataLoaderItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get("CompanyDataLoaderStep")
                .<CompanyEnum, CompanyEntity>chunk(4096)
                .reader(companyDataLoaderItemReader)
                .processor(companyDataLoaderItemProcessor)
                .writer(companyDataLoaderItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobCompanyDataLoaderStep").start(step).build();
        
    }

}
