package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.repository.CompanyRepository;
import com.arrowsoft.pcftoqaautomation.service.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDTO> getCompanyList() {
        return this.companyRepository.findAll().stream().map(CompanyDTO::new).collect(Collectors.toList());

    }

    public CompanyDTO findCompanyByID(String id) {
        return this.companyRepository.findById(Long.parseLong(id)).map(CompanyDTO::new).orElse(null);

    }

}
