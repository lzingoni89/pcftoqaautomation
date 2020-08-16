package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.typecodetodb;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBathUtil;
import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class TypeCodeToDBItemProcessor implements ItemProcessor<ImportPCFBatchTransport, EnumEntity> {

    private final ImportPCFBathUtil importPCFBathUtil;

    public TypeCodeToDBItemProcessor(ImportPCFBathUtil importPCFBathUtil) {
        this.importPCFBathUtil = importPCFBathUtil;
    }

    @Override
    public EnumEntity process(ImportPCFBatchTransport importPCFBatchTransport) throws IOException, SAXException, ParserConfigurationException {
        return this.importPCFBathUtil.createEnumEntity(importPCFBatchTransport);

    }

}
