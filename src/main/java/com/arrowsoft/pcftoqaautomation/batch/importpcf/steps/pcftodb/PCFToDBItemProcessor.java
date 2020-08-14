package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.pcftodb;


import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBathUtil;
import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PCFToDBItemProcessor implements ItemProcessor<ImportPCFBatchTransport, PCFEntity> {

    private final ImportPCFBathUtil importPCFBathUtil;

    public PCFToDBItemProcessor(ImportPCFBathUtil importPCFBathUtil) {
        this.importPCFBathUtil = importPCFBathUtil;
    }

    @Override
    public PCFEntity process(ImportPCFBatchTransport importPcfBatchTransport) throws Exception {
        return importPCFBathUtil.createPCFEntity(importPcfBatchTransport);

    }

}
