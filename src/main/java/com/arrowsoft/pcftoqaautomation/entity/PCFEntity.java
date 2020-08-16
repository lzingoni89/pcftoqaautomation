package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "gw_pcf")
public class PCFEntity extends BaseEntity {

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;

    @Column(name = "pcf_name")
    private String pcfName;

    @Column(name = "pcf_file_path")
    private String pcfFilePath;

    @Column(name = "pcf_file_name")
    private String pcfFileName;

    @Transient
    private Set<WidgetEntity> widgets = new HashSet<>();

    @Transient
    private boolean newPCF;

    public PCFEntity(ProjectEntity project,
                     String pcfName,
                     String pcfFilePath,
                     String pcfFileName) {
        this.project = project;
        this.pcfName = pcfName;
        this.pcfFilePath = pcfFilePath;
        this.pcfFileName = pcfFileName;
        this.newPCF = true;

    }


}
