package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_enum")
@EqualsAndHashCode(callSuper = true)
public class EnumEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "editable")
    private boolean editable;

    @Column(name = "value")
    private String value;

}
