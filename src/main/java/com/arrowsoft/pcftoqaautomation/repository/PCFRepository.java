package com.arrowsoft.pcftoqaautomation.repository;

import com.arrowsoft.pcftoqaautomation.entity.PCFEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PCFRepository extends JpaRepository<PCFEntity, Long> {

}
