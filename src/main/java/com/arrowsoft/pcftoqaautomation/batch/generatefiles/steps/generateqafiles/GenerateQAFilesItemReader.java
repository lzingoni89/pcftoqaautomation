package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateqafiles.dto.GenerateQAFilesTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Log4j2
@Component
public class GenerateQAFilesItemReader implements ItemReader<GenerateQAFilesTransport> {

    private final SharedBatchUtil sharedBatchUtil;
    private final PCFRepository pcfRepository;
    private Iterator<String[]> pcfNames;

    public GenerateQAFilesItemReader(SharedBatchUtil sharedBatchUtil,
                                     PCFRepository pcfRepository) {
        this.sharedBatchUtil = sharedBatchUtil;
        this.pcfRepository = pcfRepository;

    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        var project = sharedBatchUtil.getProject(stepExecution.getJobParameters());
        this.pcfNames = this.pcfRepository.findPCFNamesByProject(project).iterator();

    }

    @Override
    public GenerateQAFilesTransport read() {
        return pcfNames.hasNext() ? new GenerateQAFilesTransport(pcfNames.next()) : null;

    }

}
