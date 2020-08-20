package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "gw_widget")
public class WidgetEntity extends BaseEntity {

    @Column(name = "widget_pcf_id")
    private String widgetPCFID;

    @Column(name = "render_id")
    private String renderID;

    @Column(name = "pcf_ref")
    private String pcfRef;

    @Column(name = "enum_ref")
    private String enumRef;

    @Column(name = "pcf_name")
    private String pcfName;

    @Column(name = "pcf_path")
    private String pcfPath;

    @JoinColumn(name = "widget_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WidgetTypeEntity widgetType;

    @JoinColumn(name = "pcf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PCFEntity pcf;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WidgetEntity parent;

    @Transient
    private Set<WidgetEntity> children = new HashSet<>();

    @Transient
    private boolean newOrUpdated;

    @Transient
    private boolean needCustomEnum;

    public WidgetEntity(PCFEntity pcf, WidgetTypeEntity widgetType, Element widgetElement, WidgetEntity parent) {
        this.newOrUpdated = true;
        this.pcf = pcf;
        this.project = pcf.getProject();
        this.pcfName = pcf.getPcfName();
        this.pcfPath = pcf.getPcfFilePath();
        this.widgetType = widgetType;
        this.parent = parent;
        this.widgetPCFID = widgetElement.getAttribute("id");
        populatePCFRef(widgetElement);
        populateEnumRef(widgetElement);
        populateRenderID();

    }

    private void populatePCFRef(Element widgetElement) {
        this.pcfRef = widgetElement.getAttribute("def").split("\\(")[0];
        var widgetTypeCode = this.widgetType.getType();
        switch (widgetTypeCode) {
            case Tab:
                this.pcfRef = widgetElement.getAttribute("action").split(".go\\(")[0];
                break;
            case LocationRef:
                this.pcfRef = widgetElement.getAttribute("location").split("\\(")[0];
                this.widgetPCFID = this.pcfRef;
                break;

        }
        if (this.pcfRef.isBlank()) {
            return;

        }
        var pcfRefSpl = this.pcfRef.split("\\.");
        this.pcfRef = pcfRefSpl[pcfRefSpl.length - 1];
        if (!this.widgetPCFID.isBlank()) {
            return;
        }
        this.widgetPCFID = this.pcfRef;

    }

    private void populateEnumRef(Element widgetElement) {
        var tempEnumRef = widgetElement.getAttribute("valueType");
        if (tempEnumRef == null || tempEnumRef.isBlank()) {
            return;

        }
        if (tempEnumRef.startsWith("typekey.")) {
            this.enumRef = tempEnumRef.substring(8);
            if (!this.enumRef.contains("[")) {
                return;
            }
            this.enumRef = this.enumRef.substring(0, this.enumRef.indexOf("["));

        } else if (!widgetElement.getAttribute("valueRange").isBlank()) {
            this.enumRef = this.pcfName + (this.widgetPCFID.isBlank() ? "WidgetNotID" : this.widgetPCFID);
            this.needCustomEnum = true;

        }

    }

    private void populateRenderID() {
        var renderIDJoiner = new StringJoiner(widgetType.getRenderIDJoinerChar());
        if (this.parent != null && !this.parent.getRenderID().isBlank()) {
            if (this.widgetPCFID.isBlank() && this.widgetType.getType() == WidgetTypeEnum.Toolbar) {
                renderIDJoiner.add(this.parent.getRenderID() + "_tb");

            } else {
                renderIDJoiner.add(this.parent.getRenderID());

            }

        }
        if (!this.widgetPCFID.isBlank()) {
            if (this.widgetType.getType() == WidgetTypeEnum.LocationRef) {
                renderIDJoiner.add(this.pcfName + "_" + this.widgetPCFID);

            } else {
                renderIDJoiner.add(this.widgetPCFID);

            }

        }
        this.renderID = renderIDJoiner.toString();

    }

}
