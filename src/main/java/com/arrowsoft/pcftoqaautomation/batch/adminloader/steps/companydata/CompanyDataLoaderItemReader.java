package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.companydata;

import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

@Log4j2
@Component
public class CompanyDataLoaderItemReader implements ItemReader<CompanyEnum> {

    private Iterator<CompanyEnum> companyList;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        this.companyList = Arrays.stream(CompanyEnum.values()).iterator();

    }

    @Override
    public CompanyEnum read() {
        if (this.companyList.hasNext()) {
            return this.companyList.next();

        }
        return null;

    }
}
