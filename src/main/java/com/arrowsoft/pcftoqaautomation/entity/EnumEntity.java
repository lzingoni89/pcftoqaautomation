package com.arrowsoft.pcftoqaautomation.entity;

import com.arrowsoft.pcftoqaautomation.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "gw_enum")
public class EnumEntity extends BaseEntity {

    @Transient
    public static final String JOINER_CHAR = ",";

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;

    @Column(name = "name")
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "editable")
    private boolean editable;

    @Lob
    @Column(name = "value")
    private String value;

    @Transient
    private boolean newOrUpdated;

    public EnumEntity(ProjectEntity project, String name, String fileName, Set<String> value) {
        this.project = project;
        this.name = name;
        this.fileName = fileName;
        this.value = value != null ? getValueJoined(value) : "";
        this.editable = false;
        this.newOrUpdated = true;

    }

    public EnumEntity(ProjectEntity project, String name, String fileName, Set<String> value, boolean editable) {
        this.project = project;
        this.name = name;
        this.fileName = fileName;
        this.value = value != null ? getValueJoined(value) : "";
        this.editable = editable;
        this.newOrUpdated = true;

    }

    private String getValueJoined(Set<String> value) {
        return value.stream()
                .filter((element) -> !element.isBlank())
                .sorted()
                .reduce((partialString, element) -> partialString + JOINER_CHAR + element)
                .orElse("");

    }

}
