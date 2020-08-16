package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PCFRepository extends JpaRepository<PCFEntity, Long> {

    @Query("SELECT pcf.pcfName, pcf.pcfFilePath FROM PCFEntity pcf WHERE pcf.project = :project GROUP BY pcf.pcfName, pcf.pcfFilePath")
    Set<String[]> findPCFNamesByProject(@Param("project") ProjectEntity project);

    void deleteAllByProject(ProjectEntity project);

}
