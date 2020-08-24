package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.repository.WidgetTypeRepository;
import com.arrowsoft.pcftoqaautomation.service.dto.widget.WidgetTypeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class WidgetService {

    private final WidgetTypeRepository widgetTypeRepository;

    public WidgetService(WidgetTypeRepository widgetTypeRepository) {
        this.widgetTypeRepository = widgetTypeRepository;

    }

    public List<WidgetTypeDTO> getWidgetTypesByVersion(GWVersionEnum version) {
        return widgetTypeRepository
                .findAllByVersion(version)
                .stream().map(WidgetTypeDTO::new)
                .collect(Collectors.toList());

    }

    public void saveWidgetType(WidgetTypeDTO widgetTypeDTO) {
        if (widgetTypeDTO == null) {
            return;

        }
        var widget = this.widgetTypeRepository.findById(widgetTypeDTO.getId()).orElse(null);
        if (widget == null) {
            log.error("Widget Type not found: " + widgetTypeDTO.getId());
            return;

        }
        widget.setPrefix(widgetTypeDTO.getPrefix());
        widget.setFindBy(widgetTypeDTO.getFindBy());
        widget.setMigrate(widgetTypeDTO.isMigrate());
        this.widgetTypeRepository.saveAndFlush(widget);

    }

}
