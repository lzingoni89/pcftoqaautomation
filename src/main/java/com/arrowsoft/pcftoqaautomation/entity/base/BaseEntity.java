package com.arrowsoft.pcftoqaautomation.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @CreatedDate
    @Column(name = "CreatedDate", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "LastModifiedDate")
    private Instant lastModifiedDate;

    @PrePersist
    public void prePersistEntity() {
        this.createdDate = Instant.now();

    }

    @PreUpdate
    public void preUpdateEntity() {
        this.lastModifiedDate = Instant.now();

    }


}
