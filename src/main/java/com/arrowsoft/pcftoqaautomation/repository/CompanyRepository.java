package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsCompanyByCompanyCodIntern(CompanyEnum companyCodIntern);
    CompanyEntity findFirstByCompanyCodIntern(CompanyEnum companyCodIntern);
    List<CompanyEntity> findAllByOrderById();

}
