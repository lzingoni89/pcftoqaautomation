package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.pcftodb;


import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBathUtil;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Log4j2
@Component
public class PCFToDBItemProcessor implements ItemProcessor<ImportPCFBatchTransport, PCFEntity> {

    private final ImportPCFBathUtil importPCFBathUtil;
    private final SharedBatchUtil sharedBatchUtil;
    private final WidgetTypeRepository widgetTypeRepository;
    private Set<WidgetTypeEntity> widgetTypeEntitySet;

    public PCFToDBItemProcessor(ImportPCFBathUtil importPCFBathUtil,
                                SharedBatchUtil sharedBatchUtil, WidgetTypeRepository widgetTypeRepository) {
        this.importPCFBathUtil = importPCFBathUtil;
        this.sharedBatchUtil = sharedBatchUtil;
        this.widgetTypeRepository = widgetTypeRepository;

    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        var project = sharedBatchUtil.getProject(stepExecution.getJobParameters());
        this.widgetTypeEntitySet = widgetTypeRepository.findAllByVersion(project.getVersion());

    }

    @Override
    public PCFEntity process(ImportPCFBatchTransport importPcfBatchTransport) throws Exception {
        return importPCFBathUtil.createPCFEntity(importPcfBatchTransport, widgetTypeEntitySet);

    }

}
