package com.arrowsoft.pcftoqaautomation.service.dto.widget;

import com.arrowsoft.pcftoqaautomation.entity.WidgetTypeEntity;
import com.arrowsoft.pcftoqaautomation.enums.HowFindEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetPrefixEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetTypeEnum;
import com.arrowsoft.pcftoqaautomation.enums.YesNoEnum;
import lombok.Data;

@Data
public class WidgetTypeDTO {

    private Long id;
    private WidgetTypeEnum type;
    private boolean migrate;
    private WidgetPrefixEnum prefix;
    private HowFindEnum findBy;

    public WidgetTypeDTO(WidgetTypeEntity widgetTypeEntity) {
        this.id = widgetTypeEntity.getId();
        this.type = widgetTypeEntity.getType();
        this.migrate = widgetTypeEntity.isMigrate();
        this.prefix = widgetTypeEntity.getPrefix();
        this.findBy = widgetTypeEntity.getFindBy();

    }

    public String getTypeView() {
        return this.type.toString();

    }

    public YesNoEnum getMigrateView() {
        return this.migrate ? YesNoEnum.Yes : YesNoEnum.No;

    }

    public String getPrefixView() {
        return this.prefix.getPrefix();

    }

    public String getFindByView() {
        return this.findBy.getHow();

    }

}
