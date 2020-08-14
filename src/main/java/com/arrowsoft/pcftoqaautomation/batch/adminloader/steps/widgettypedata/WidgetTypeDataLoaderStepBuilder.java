package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.widgettypedata;

import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WidgetTypeDataLoaderStepBuilder {

    private final StepBuilderFactory stepBuilderFactory;
    private final WidgetTypeDataLoaderItemReader widgetTypeDataLoaderItemReader;
    private final WidgetTypeDataLoaderItemProcessor widgetTypeDataLoaderItemProcessor;
    private final WidgetTypeDataLoaderItemWriter widgetTypeDataLoaderItemWriter;

    public WidgetTypeDataLoaderStepBuilder(StepBuilderFactory stepBuilderFactory,
                                           WidgetTypeDataLoaderItemReader widgetTypeDataLoaderItemReader,
                                           WidgetTypeDataLoaderItemProcessor widgetTypeDataLoaderItemProcessor,
                                           WidgetTypeDataLoaderItemWriter widgetTypeDataLoaderItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.widgetTypeDataLoaderItemReader = widgetTypeDataLoaderItemReader;
        this.widgetTypeDataLoaderItemProcessor = widgetTypeDataLoaderItemProcessor;
        this.widgetTypeDataLoaderItemWriter = widgetTypeDataLoaderItemWriter;

    }

    public Flow getStep() {
        var step = stepBuilderFactory.get("WidgetTypeDataLoaderStep")
                .<WidgetTypeEnum, WidgetTypeEntity>chunk(4096)
                .reader(widgetTypeDataLoaderItemReader)
                .processor(widgetTypeDataLoaderItemProcessor)
                .writer(widgetTypeDataLoaderItemWriter)
                .build();
        return new FlowBuilder<Flow>("FlowJobWidgetTypeDataLoaderStep").start(step).build();

    }

}
