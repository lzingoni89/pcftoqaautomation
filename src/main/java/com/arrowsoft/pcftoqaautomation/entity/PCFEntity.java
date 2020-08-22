package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Element;

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

    @Column(name = "menu_action_ref")
    private String menuActionRef;

    @Column(name = "acc_menu_action_ref")
    private String acceleratedMenuActions;

    @Column(name = "mode")
    private String mode;

    @JoinColumn(name = "pcf_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WidgetTypeEntity pcfType;

    @Transient
    private Set<WidgetEntity> widgets = new HashSet<>();

    @Transient
    private boolean newPCF;

    public PCFEntity(ProjectEntity project,
                     String pcfName,
                     String pcfFilePath,
                     WidgetTypeEntity pcfType,
                     Element containerElement) {
        this.project = project;
        this.pcfName = pcfName;
        this.pcfFilePath = pcfFilePath;
        this.menuActionRef = containerElement.getAttribute("menuActions").split("\\(")[0];
        this.acceleratedMenuActions = containerElement.getAttribute("acceleratedMenuActions").split("\\(")[0];
        this.mode = containerElement.getAttribute("mode");
        this.pcfType = pcfType;
        this.newPCF = true;

    }


}
