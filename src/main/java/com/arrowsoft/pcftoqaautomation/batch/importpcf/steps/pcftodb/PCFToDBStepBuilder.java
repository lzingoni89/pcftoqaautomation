package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.pcftodb;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBatchConst;
import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PCFToDBStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final PCFToDBItemReader pcfToDBItemReader;
    private final PCFToDBItemProcessor pcfToDBItemProcessor;
    private final PCFToDBItemWriter pcfToDBItemWriter;

    public PCFToDBStepBuilder(StepBuilderFactory stepBuilderFactory,
                              PCFToDBItemReader pcfToDBItemReader,
                              PCFToDBItemProcessor pcfToDBItemProcessor,
                              PCFToDBItemWriter pcfToDBItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.pcfToDBItemReader = pcfToDBItemReader;
        this.pcfToDBItemProcessor = pcfToDBItemProcessor;
        this.pcfToDBItemWriter = pcfToDBItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get(ImportPCFBatchConst.PCF_IMPORT_STEP)
                .<ImportPCFBatchTransport, PCFEntity>chunk(4096)
                .reader(pcfToDBItemReader)
                .processor(pcfToDBItemProcessor)
                .writer(pcfToDBItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobPCFToDBBatchStep").start(step).build();

    }

}
