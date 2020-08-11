package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_pcf")
@EqualsAndHashCode(callSuper = true)
public class PCFEntity extends BaseEntity {

    @Column(name = "pcf_name")
    private String pcfName;

    @Column(name = "pcf_folder_path")
    private String pcfFilePath;

    @Column(name = "pcf_file_name")
    private String pcfFileName;

    @OneToMany(mappedBy = "pcf", fetch = FetchType.LAZY)
    private Set<WidgetEntity> widgets;

    public PCFEntity(String pcfName, String pcfFilePath) {
        this.pcfName = pcfName;
        this.pcfFilePath = pcfFilePath;

    }


}
