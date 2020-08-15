package com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums;

import com.arrowsoft.pcftoqaautomation.batch.generatefiles.steps.generateenums.dto.GenerateEnumsTransport;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchUtil;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Log4j2
@Component
public class GenerateEnumsItemReader implements ItemReader<GenerateEnumsTransport> {

    private final SharedBatchUtil sharedBatchUtil;
    private final EnumRepository enumRepository;
    private ProjectEntity project;
    private Iterator<String> enumNames;

    public GenerateEnumsItemReader(SharedBatchUtil sharedBatchUtil, EnumRepository enumRepository) {
        this.sharedBatchUtil = sharedBatchUtil;
        this.enumRepository = enumRepository;

    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.project = sharedBatchUtil.getProject(stepExecution);
        this.enumNames = enumRepository.findEnumNamesByProject(project).iterator();

    }

    @Override
    public GenerateEnumsTransport read() {
        if (enumNames == null || !enumNames.hasNext()) {
            return null;
        }
        return new GenerateEnumsTransport(this.project, enumNames.next());

    }


}
