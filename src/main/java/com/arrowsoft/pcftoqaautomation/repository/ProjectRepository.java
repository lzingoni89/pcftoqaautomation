package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.CompanyEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    boolean existsProjectByCompanyAndModule(CompanyEntity company, ModuleEnum module);

    ProjectEntity findFirstByCompanyAndModuleAndVersion(CompanyEntity company, ModuleEnum module, GWVersionEnum version);

    List<ProjectEntity> findAllByCompany_Id(Long company_id);

}
