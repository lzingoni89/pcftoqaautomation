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
@Table(name = "gw_widget")
public class WidgetEntity extends BaseEntity {

    @Column(name = "widget_pcf_id")
    private String widgetPCFID;

    @Column(name = "render_id")
    private String renderID;

    @Column(name = "ref_pcf")
    private String refPCF;

    @JoinColumn(name = "widget_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WidgetTypeEntity widgetType;

    @JoinColumn(name = "pcf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PCFEntity pcf;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WidgetEntity parent;

    @Transient
    private Set<WidgetEntity> children = new HashSet<>();

    @Transient
    private boolean newOrUpdated;

    public WidgetEntity(PCFEntity pcf, WidgetTypeEntity widgetType, Element widgetElement, WidgetEntity parent) {
        this.pcf = pcf;
        this.widgetType = widgetType;
        this.widgetPCFID = widgetElement.getAttribute("id");
        this.refPCF = widgetElement.getAttribute("def");
        this.newOrUpdated = true;
        if (parent != null) {
            this.parent = parent;
            this.renderID = parent.getRenderID() + widgetType.getRenderIDJoinerChar() + this.widgetPCFID;

        } else {
            this.renderID = this.widgetPCFID;

        }

    }

}
