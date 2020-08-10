package com.arrowsoft.pcftoqaautomation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_widget")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WidgetEntity {

    @Id
    @Column(name="pk_widget")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToMany(mappedBy = "container")
    private Set<PCFEntity> pcfFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parent_widget")
    private WidgetEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    public Set<WidgetEntity> children = new HashSet<>();

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
        if (parent != null) {
            this.parent = parent;
            this.renderID = parent.getRenderID() + "-" + widgetID;

        } else {
            this.renderID = this.widgetID;

        }

    }

}
