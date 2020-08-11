package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_company")
@EqualsAndHashCode(callSuper = true)
public class CompanyEntity extends BaseEntity {

    @Column(name = "cod_namespace")
    private String codNamespace;

    @Column(name = "name")
    private String name;

    @Column(name = "company_cod_intern")
    @Enumerated(EnumType.STRING)
    private CompanyEnum companyCodIntern;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<ProjectEntity> projects;

    public CompanyEntity(CompanyEnum companyEnum) {
        this.codNamespace = companyEnum.getCodNamespace();
        this.name = companyEnum.getName();
        this.companyCodIntern = companyEnum;

    }

}
