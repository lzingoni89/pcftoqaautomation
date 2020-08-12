package com.arrowsoft.pcftoqaautomation.batch.adminloader.steps.projectdata;

import com.arrowsoft.pcftoqaautomation.entity.ProjectEntity;
import com.arrowsoft.pcftoqaautomation.repository.ProjectRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ProjectDataLoaderItemWriter implements ItemWriter<Set<ProjectEntity>> {

    private final ProjectRepository projectRepository;

    public ProjectDataLoaderItemWriter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void write(List<? extends Set<ProjectEntity>> list) {
        for (Set<ProjectEntity> set : list) {
            projectRepository.saveAll(set);
        }
    }
}
