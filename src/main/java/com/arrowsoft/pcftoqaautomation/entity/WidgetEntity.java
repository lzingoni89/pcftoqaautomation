package com.arrowsoft.pcftoqaautomation.entity;

import lombok.Data;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "gw_widget")
public class WidgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "container")
    private Set<PCFEntity> pcfFile;

    @ManyToOne
    private WidgetEntity parent;

    @OneToMany(mappedBy="parent")
    private Set<WidgetEntity> children;

    private String widget;
    private String widgetID;
    private String refPCF;
    private String renderID;

    public WidgetEntity(Element widgetElement) {
        this.widget = widgetElement.getTagName();
        this.widgetID = widgetElement.getAttribute("id");
        this.refPCF = widgetElement.getAttribute("def");
        this.renderID = this.widgetID;

    }

    public WidgetEntity(Element widgetElement, WidgetEntity parent) {
        this.widget = widgetElement.getTagName();
        this.widgetID = widgetElement.getAttribute("id");
        this.refPCF = widgetElement.getAttribute("def");
        if(parent != null) {
            this.parent = parent;
            this.renderID = parent.getRenderID() + "-" + widgetID;

        } else {
            this.renderID = this.widgetID;

        }

    }

}
