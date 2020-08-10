package com.arrowsoft.pcftoqaautomation.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gw_pcf")
public class PCFEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pcfName;
    private String pcfFilePath;
    @ManyToOne
    private WidgetEntity container;

    public PCFEntity(String pcfName, String pcfFilePath, WidgetEntity container) {
        this.pcfName = pcfName;
        this.pcfFilePath = pcfFilePath;
        this.container = container;

    }


}
