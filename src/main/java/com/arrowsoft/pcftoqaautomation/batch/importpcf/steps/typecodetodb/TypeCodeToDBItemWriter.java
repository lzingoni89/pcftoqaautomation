package com.arrowsoft.pcftoqaautomation.batch.importpcf.steps.typecodetodb;

import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.repository.EnumRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class TypeCodeToDBItemWriter implements ItemWriter<EnumEntity> {

    private final EnumRepository enumRepository;

    public TypeCodeToDBItemWriter(EnumRepository enumRepository) {
        this.enumRepository = enumRepository;
    }

    @Override
    public void write(List<? extends EnumEntity> list) {
        enumRepository.saveAll(list);

    }

}
