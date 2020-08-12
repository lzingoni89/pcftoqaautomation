package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_project")
@EqualsAndHashCode(callSuper = true)
public class ProjectEntity extends BaseEntity {

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private ModuleEnum module;

    @Column(name = "version")
    @Enumerated(EnumType.STRING)
    private GWVersionEnum version;

    @Column(name = "root_path")
    private String rootPath;

    @Column(name = "admin_git_repository")
    private boolean adminGitRepository;

    public ProjectEntity(CompanyEntity company, ModuleEnum module) {
        this.company = company;
        this.module = module;
        this.version = GWVersionEnum.VER_10;
        this.adminGitRepository = false;

    }

}
