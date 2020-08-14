package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetEntity;
import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Long> {

    WidgetEntity findFirstByPcfAndWidgetTypeAndWidgetPCFID(PCFEntity pcf, WidgetTypeEntity widgetType, String widgetPCFID);

}
