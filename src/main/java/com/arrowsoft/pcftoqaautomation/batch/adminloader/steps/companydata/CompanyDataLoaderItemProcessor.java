package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CompanyDataLoaderItemProcessor implements ItemProcessor<CompanyEnum, CompanyEntity> {

    private final CompanyRepository companyRepository;

    public CompanyDataLoaderItemProcessor(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyEntity process(CompanyEnum companyEnum) {
        if (this.companyRepository.existsCompanyByCompanyCodIntern(companyEnum)) {
            return null;
        }
        return new CompanyEntity(companyEnum);

    }
}
