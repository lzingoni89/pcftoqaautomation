package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WidgetTypeRepository extends JpaRepository<WidgetTypeEntity, Long> {

    boolean existsWidgetTypeByTypeAndVersion(WidgetTypeEnum type, GWVersionEnum version);

    Set<WidgetTypeEntity> findAllByVersion(GWVersionEnum version);

    Set<WidgetTypeEntity> findAllByVersionAndMigrate(GWVersionEnum version, boolean migrate);

    WidgetTypeEntity findFirstByTypeAndVersion(WidgetTypeEnum type, GWVersionEnum version);

}
