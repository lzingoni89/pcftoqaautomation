package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Long> {

    List<WidgetEntity> findByPcfNameOrderById(String pcfName);

    List<WidgetEntity> findByPcfNameAndPcfPathOrderById(String pcfName, String pcfPath);

    void deleteAllByProject(ProjectEntity project);

}
