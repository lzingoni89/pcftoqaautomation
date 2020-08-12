package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.projectdata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Log4j2
@Component
public class ProjectDataLoaderItemReader implements ItemReader<CompanyEntity> {

    private final CompanyRepository companyRepository;
    private Iterator<CompanyEntity> companies;

    public ProjectDataLoaderItemReader(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.companies = companyRepository.findAll().iterator();

    }

    @Override
    public CompanyEntity read() {
        return companies.hasNext() ? companies.next() : null;

    }

}
