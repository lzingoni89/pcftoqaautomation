package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.CompanyEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "gw_company")
public class CompanyEntity extends BaseEntity {

    @Column(name = "cod_namespace")
    private String codNamespace;

    @Column(name = "name")
    private String name;

    @Column(name = "retired")
    private boolean retired;

    @Column(name = "company_cod_intern")
    @Enumerated(EnumType.STRING)
    private CompanyEnum companyCodIntern;

    public CompanyEntity(CompanyEnum companyEnum) {
        this.codNamespace = companyEnum.getCodNamespace();
        this.name = companyEnum.getName();
        this.companyCodIntern = companyEnum;
        this.retired = false;

    }

}
