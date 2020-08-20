package com.arrowsoft.pcftoqaautomation.service.dto.company;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import com.arrowsoft.pcftoqaautomation.enums.YesNoEnum;
import lombok.Data;

@Data
public class ProjectDTO {

    private Long id;
    private ModuleEnum module;
    private GWVersionEnum version;
    private String rootPath;
    private boolean adminGitRepository;

    public ProjectDTO(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.module = projectEntity.getModule();
        this.version = projectEntity.getVersion();
        this.rootPath = projectEntity.getRootPath();
        this.adminGitRepository = projectEntity.isAdminGitRepository();

    }

    public String getModuleView() {
        return this.module.getDesc();

    }

    public String getVersionView() {
        return this.version.getDesc();

    }

    public YesNoEnum getAdminGitRepositoryView() {
        return this.adminGitRepository ? YesNoEnum.Yes : YesNoEnum.No;

    }

}
