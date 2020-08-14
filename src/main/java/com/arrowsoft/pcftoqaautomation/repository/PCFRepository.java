package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PCFRepository extends JpaRepository<PCFEntity, Long> {

    PCFEntity findFirstByProjectAndPcfFileName(ProjectEntity project, String pcfFileName);

}
