package com.arrowsoft.pcftoqaautomation.web.enums;

import com.arrowsoft.pcftoqaautomation.entity.EnumEntity;
import com.arrowsoft.pcftoqaautomation.service.EnumsService;
import com.arrowsoft.pcftoqaautomation.service.dto.enums.EnumDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Log4j2
@Route(value = "enum", layout = MainLayout.class)
@PageTitle("Enum | Guidewire ETL")
public class EnumsEditPage extends VerticalLayout implements HasUrlParameter<String> {

    private final H1 title;
    private final EnumsService enumsService;
    private final Set<String> valuesLoaded;
    private EnumDTO enumDTO;
    private VerticalLayout valueList;

    public EnumsEditPage(EnumsService enumsService) {
        this.enumsService = enumsService;
        valuesLoaded = new HashSet<>();
        this.title = createTitle();

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String enumID) {
        enumDTO = enumsService.getEnumByID(enumID);
        title.setText(enumDTO.getName());
        createValueList();

    }

    private H1 createTitle() {
        var title = new H1();
        title.getStyle().set("color", "gray");
        return title;

    }

    private void createValueList() {
        valueList = new VerticalLayout();
        for (var value : enumDTO.getValue().split(EnumEntity.JOINER_CHAR)) {
            if (value.isBlank()) {
                continue;

            }
            populateValueList(value);
        }
        var valueField = new TextField();
        var addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            populateValueList(valueField.getValue());
            valueField.clear();

        });
        var saveButton = new Button("Save");
        saveButton.addClickListener(click -> {
            save();
            goBack();

        });
        var cancelButton = new Button("Cancel");
        cancelButton.addClickListener(click -> {
            goBack();

        });

        add(new HorizontalLayout(valueField, addButton));
        add(new HorizontalLayout(saveButton, cancelButton));
        add(createValuesTitle(), valueList);

    }

    private void populateValueList(String value) {
        if (value == null
                || value.isBlank()
                || valuesLoaded.contains(value)) {
            return;

        }
        valuesLoaded.add(value);
        var valueField = new TextField();
        valueField.setValue(value);
        var deleteButton = new Button("Delete");
        var hLayout = new HorizontalLayout(valueField, deleteButton);
        deleteButton.addClickListener(deleteClick -> {
            valueList.remove(hLayout);

        });
        valueList.add(hLayout);
    }

    private H3 createValuesTitle() {
        var title = new H3();
        title.setText("Values");
        title.getStyle().set("color", "gray");
        return title;

    }

    private void save() {
        var joiner = new StringJoiner(EnumEntity.JOINER_CHAR);
        for (var hl : valueList.getChildren().collect(Collectors.toList())) {
            var tf = (TextField) hl
                    .getChildren()
                    .filter((element) -> element instanceof TextField)
                    .findFirst()
                    .orElse(null);
            if (tf == null || tf.getValue().isBlank()) {
                continue;

            }
            joiner.add(tf.getValue());

        }

        enumDTO.setValue(joiner.toString());
        enumsService.saveEnum(enumDTO);
    }

    private void goBack() {
        getUI().ifPresent(ui ->
                ui.navigate(EnumsViewPage.class, enumDTO.getIdCompany().toString())
        );

    }

}
