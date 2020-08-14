package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.dto;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import lombok.Getter;

import java.io.File;

@Getter
public final class ImportPCFBatchTransport {

    private final File file;
    private final ProjectEntity project;

    public ImportPCFBatchTransport(File file, ProjectEntity project) {
        this.file = file;
        this.project = project;
    }

}
