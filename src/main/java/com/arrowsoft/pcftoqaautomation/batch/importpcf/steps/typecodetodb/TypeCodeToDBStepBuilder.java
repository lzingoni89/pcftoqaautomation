package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.typecodetodb;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBatchConst;
import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TypeCodeToDBStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final TypeCodeToDBItemReader typeCodeToDBItemReader;
    private final TypeCodeToDBItemProcessor typeCodeToDBItemProcessor;
    private final TypeCodeToDBItemWriter typeCodeToDBItemWriter;

    public TypeCodeToDBStepBuilder(StepBuilderFactory stepBuilderFactory,
                                   TypeCodeToDBItemReader typeCodeToDBItemReader,
                                   TypeCodeToDBItemProcessor typeCodeToDBItemProcessor,
                                   TypeCodeToDBItemWriter typeCodeToDBItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.typeCodeToDBItemReader = typeCodeToDBItemReader;
        this.typeCodeToDBItemProcessor = typeCodeToDBItemProcessor;
        this.typeCodeToDBItemWriter = typeCodeToDBItemWriter;

    }

    public Flow getStep() {
        var stepMetadata = stepBuilderFactory.get(ImportPCFBatchConst.TYPECODE_METADATA_IMPORT_STEP)
                .<ImportPCFBatchTransport, EnumEntity>chunk(4096)
                .reader(typeCodeToDBItemReader)
                .processor(typeCodeToDBItemProcessor)
                .writer(typeCodeToDBItemWriter)
                .build();

        var stepExtension = stepBuilderFactory.get(ImportPCFBatchConst.TYPECODE_EXTENSION_IMPORT_STEP)
                .<ImportPCFBatchTransport, EnumEntity>chunk(4096)
                .reader(typeCodeToDBItemReader)
                .processor(typeCodeToDBItemProcessor)
                .writer(typeCodeToDBItemWriter)
                .build();

        return new FlowBuilder<Flow>("FlowJobTypeCodeToDBBatchStep").start(stepMetadata).next(stepExtension).build();

    }


}
