package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetHowFindEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetPrefixEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "gw_widgettype")
public class WidgetTypeEntity extends BaseEntity {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private WidgetTypeEnum type;

    @Column(name = "version")
    @Enumerated(EnumType.STRING)
    private GWVersionEnum version;

    @Column(name = "migrate")
    private boolean migrate;

    @Column(name = "prefix")
    @Enumerated(EnumType.STRING)
    private WidgetPrefixEnum prefix;

    @Column(name = "find_by")
    @Enumerated(EnumType.STRING)
    private WidgetHowFindEnum findBy;

    public WidgetTypeEntity(WidgetTypeEnum type, GWVersionEnum version) {
        this.type = type;
        this.version = version;
        this.migrate = true;
        this.prefix = WidgetPrefixEnum.TXT;
        this.findBy = WidgetHowFindEnum.ID;

    }

    public String getRenderIDJoinerChar() {
        return version.getRenderIDCharJoiner();

    }

}
