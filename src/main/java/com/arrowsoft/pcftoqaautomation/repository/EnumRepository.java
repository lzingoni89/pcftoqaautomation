package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface EnumRepository extends JpaRepository<EnumEntity, Long> {

    boolean existsByProjectAndName(ProjectEntity project, String name);

    @Query("SELECT e.name FROM EnumEntity e WHERE e.project = :project GROUP BY e.name")
    Set<String> findEnumNamesByProject(@Param("project") ProjectEntity project);

    Set<EnumEntity> findByProjectAndName(ProjectEntity project, String name);

    void deleteAllByProjectAndEditable(ProjectEntity project, boolean editable);

}
