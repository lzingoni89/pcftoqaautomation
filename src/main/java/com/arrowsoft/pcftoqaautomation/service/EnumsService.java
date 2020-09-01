package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import com.arrowsoft.pcftoqaautomation.service.dto.enums.EnumDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class EnumsService {

    private final EnumRepository enumRepository;

    public EnumsService(EnumRepository enumRepository) {
        this.enumRepository = enumRepository;
    }

    public List<EnumDTO> getCustomEnumsByProjectId(Long projectId) {
        return this.enumRepository
                .findAllByProjectIdAndEditableTrue(projectId)
                .stream().map(EnumDTO::new)
                .collect(Collectors.toList());

    }

    public EnumDTO getEnumByID(String id) {
        var enumLongID = Long.parseLong(id);
        var enumDTO = this.enumRepository
                .findById(enumLongID)
                .map(EnumDTO::new)
                .orElse(null);
        if (enumDTO == null) {
            return null;

        }
        enumDTO.setIdCompany(enumRepository.getCompanyIDByEnum(enumLongID));
        return enumDTO;

    }

    public void saveEnum(EnumDTO enumDTO) {
        if (enumDTO == null) {
            return;

        }
        var enumEntity = this.enumRepository.findById(enumDTO.getId()).orElse(null);
        if (enumEntity == null) {
            log.error("Enum not found: " + enumDTO.getId());
            return;

        }
        //enumDTO.getValue().stream().reduce("", (partialString, element) -> partialString + EnumEntity.JOINER_CHAR + element)
        enumEntity.setValue(enumDTO.getValue());
        this.enumRepository.saveAndFlush(enumEntity);

    }

}
