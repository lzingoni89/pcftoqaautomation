package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnumRepository extends JpaRepository<EnumEntity, Long> {

    EnumEntity findFirstByProjectAndFileNameAndEditable(ProjectEntity project, String fileName, boolean editable);

}
