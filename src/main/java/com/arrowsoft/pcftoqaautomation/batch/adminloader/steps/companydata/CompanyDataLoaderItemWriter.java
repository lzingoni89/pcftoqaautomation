package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class CompanyDataLoaderItemWriter implements ItemWriter<CompanyEntity> {

    private final CompanyRepository companyRepository;

    public CompanyDataLoaderItemWriter(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }

    @Override
    public void write(List<? extends CompanyEntity> list) {
        this.companyRepository.saveAll(list);

    }
}
