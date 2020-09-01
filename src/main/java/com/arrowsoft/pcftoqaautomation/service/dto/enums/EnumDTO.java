package com.arrowsoft.pcftoqaautomation.service.dto.enums;

import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import lombok.Data;

@Data
public class EnumDTO {

    private Long id;
    private String name;
    private String value;
    private Long idCompany;

    public EnumDTO(EnumEntity enumEntity) {
        this.id = enumEntity.getId();
        this.name = enumEntity.getName();
        this.value = enumEntity.getValue();

    }

}
