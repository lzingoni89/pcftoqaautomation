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
        this.widgetType = widgetType;
        this.parent = parent;
        this.widgetPCFID = widgetElement.getAttribute("id");
        this.pcfRef = widgetElement.getAttribute("def").split("\\(")[0];
        if (this.pcfRef.isBlank() && this.widgetType.getType() == WidgetTypeEnum.Tab) {
            this.pcfRef = widgetElement.getAttribute("action").split(".go\\(")[0];

        }

        var tempEnumRef = widgetElement.getAttribute("valueType");
        if (tempEnumRef != null) {
            if (tempEnumRef.startsWith("typekey.")) {
                this.enumRef = tempEnumRef.substring(8);

            } else if (!widgetElement.getAttribute("valueRange").isBlank()) {
                this.enumRef = this.pcfName + (this.widgetPCFID.isBlank() ? "WidgetNotID" : this.widgetPCFID);
                this.needCustomEnum = true;

            }

        }
        var renderIDJoiner = new StringJoiner(widgetType.getRenderIDJoinerChar());
        if (this.parent != null && !this.parent.getRenderID().isBlank()) {
            renderIDJoiner.add(this.parent.getRenderID());

        }
        if (!this.widgetPCFID.isBlank()) {
            renderIDJoiner.add(this.widgetPCFID);

        }
        this.renderID = renderIDJoiner.toString();
        if (this.widgetType.getType() == WidgetTypeEnum.LocationRef) {
            this.widgetPCFID = this.widgetPCFID.isBlank() ? this.parent.getWidgetPCFID() + "_" + widgetElement.getAttribute("location").split("\\(")[0] : this.widgetPCFID;
            var grandParent = this.parent.parent;
            this.renderID = grandParent != null ? grandParent.renderID + widgetType.getRenderIDJoinerChar() + this.widgetPCFID : this.widgetPCFID;
            this.widgetPCFID = this.widgetPCFID.replace("_", "");

        }

    }

}
