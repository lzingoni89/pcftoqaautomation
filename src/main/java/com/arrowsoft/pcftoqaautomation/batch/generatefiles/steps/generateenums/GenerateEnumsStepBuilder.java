package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBatchConst;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateEnumsStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final GenerateEnumsItemReader generateEnumsItemReader;
    private final GenerateEnumsItemProcessor generateEnumsItemProcessor;
    private final GenerateEnumsItemWriter generateEnumsItemWriter;

    public GenerateEnumsStepBuilder(StepBuilderFactory stepBuilderFactory,
                                    GenerateEnumsItemReader generateEnumsItemReader,
                                    GenerateEnumsItemProcessor generateEnumsItemProcessor,
                                    GenerateEnumsItemWriter generateEnumsItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.generateEnumsItemReader = generateEnumsItemReader;
        this.generateEnumsItemProcessor = generateEnumsItemProcessor;
        this.generateEnumsItemWriter = generateEnumsItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get(ImportPCFBatchConst.GENERATE_ENUMS_STEP)
                .<GenerateEnumsTransport, GenerateEnumsTransport>chunk(4096)
                .reader(generateEnumsItemReader)
                .processor(generateEnumsItemProcessor)
                .writer(generateEnumsItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobGenerateEnumsBatchStep").start(step).build();

    }

}
