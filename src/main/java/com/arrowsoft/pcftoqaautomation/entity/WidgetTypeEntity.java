package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetHowFindEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetPrefixEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "gw_widgettype")
@EqualsAndHashCode(callSuper = true)
public class WidgetTypeEntity extends BaseEntity {

    @Column(name = "version")
    @Enumerated(EnumType.STRING)
    private GWVersionEnum version;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private WidgetTypeEnum type;

    @Column(name = "migrate")
    private boolean migrate;

    @Column(name = "prefix")
    @Enumerated(EnumType.STRING)
    private WidgetPrefixEnum prefix;

    @Column(name = "find_by")
    @Enumerated(EnumType.STRING)
    private WidgetHowFindEnum findBy;

}
