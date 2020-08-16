package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBatchConst;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateQAFilesStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final GenerateQAFilesItemReader generateQAFilesItemReader;
    private final GenerateQAFilesItemProcessor generateQAFilesItemProcessor;
    private final GenerateQAFilesItemWriter generateQAFilesItemWriter;

    public GenerateQAFilesStepBuilder(StepBuilderFactory stepBuilderFactory,
                                      GenerateQAFilesItemReader generateQAFilesItemReader,
                                      GenerateQAFilesItemProcessor generateQAFilesItemProcessor,
                                      GenerateQAFilesItemWriter generateQAFilesItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.generateQAFilesItemReader = generateQAFilesItemReader;
        this.generateQAFilesItemProcessor = generateQAFilesItemProcessor;
        this.generateQAFilesItemWriter = generateQAFilesItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get(ImportPCFBatchConst.GENERATE_QA_STEP)
                .<GenerateQAFilesTransport, GenerateQAFilesTransport>chunk(4096)
                .reader(generateQAFilesItemReader)
                .processor(generateQAFilesItemProcessor)
                .writer(generateQAFilesItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobGenerateQABatchStep").start(step).build();

    }

}
