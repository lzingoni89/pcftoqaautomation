package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.typecodetodb;

import com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto.ImportPCFBatchTransport;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBatchConst;
import com.arrowsoft.pcftoqaautomation.batch.importpcf.util.ImportPCFBathUtil;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchMsg;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;

@Log4j2
@Component
public class TypeCodeToDBItemReader implements ItemReader<ImportPCFBatchTransport> {

    private final ImportPCFBathUtil importPCFBathUtil;
    private final SharedBatchUtil sharedBatchUtil;
    private Iterator<File> files;
    private ProjectEntity project;

    public TypeCodeToDBItemReader(ImportPCFBathUtil importPCFBathUtil,
                                  SharedBatchUtil sharedBatchUtil) {
        this.importPCFBathUtil = importPCFBathUtil;
        this.sharedBatchUtil = sharedBatchUtil;
    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.project = sharedBatchUtil.getProject(stepExecution);
        switch (stepExecution.getStepName()) {
            case ImportPCFBatchConst.TYPECODE_METADATA_IMPORT_STEP:
                this.files = importPCFBathUtil.getTypeCodeMetadataFiles(this.project);
                break;
            case ImportPCFBatchConst.TYPECODE_EXTENSION_IMPORT_STEP:
                this.files = importPCFBathUtil.getTypeCodeExtensionFiles(this.project);
                break;
            default:
                log.error(SharedBatchMsg.ERROR_TYPECODE_STEP_NOT_FOUND);

        }

    }

    @Override
    public ImportPCFBatchTransport read() {
        if (this.files == null || !this.files.hasNext()) {
            return null;

        }
        return new ImportPCFBatchTransport(this.files.next(), this.project);

    }

}
