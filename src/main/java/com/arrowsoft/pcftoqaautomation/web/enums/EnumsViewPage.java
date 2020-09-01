package com.arrowsoft.pcftoqaautomation.web.enums;

import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.EnumsService;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.ProjectDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.enums.EnumDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.apache.commons.lang3.StringUtils;

@Route(value = "enums", layout = MainLayout.class)
@PageTitle("Enums | Guidewire ETL")
public class EnumsViewPage extends VerticalLayout implements HasUrlParameter<String> {

    private final CompanyService companyService;
    private final EnumsService enumsService;
    private final H1 title;
    private CompanyDTO companyDTO;

    public EnumsViewPage(EnumsService enumsService, CompanyService companyService) {
        this.enumsService = enumsService;
        this.companyService = companyService;

        this.title = createTitle();

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        if (parameter.isBlank()) {
            parameter = "1";

        }
        this.companyDTO = companyService.findCompanyByID(parameter);
        this.title.setText(this.companyDTO.getName());
        createEnumGrids();

    }

    private H1 createTitle() {
        var title = new H1();
        title.getStyle().set("color", "gray");
        return title;

    }

    private void createEnumGrids() {
        removeAll();
        add(this.title);
        for (ProjectDTO projectDTO : this.companyDTO.getProjectList()) {
            var items = this.enumsService.getCustomEnumsByProjectId(projectDTO.getId());
            if (items == null || items.isEmpty()) {
                continue;
            }
            var projectTitle = new H3(projectDTO.getModuleView() + "(" + projectDTO.getVersionView() + ")");
            projectTitle.getStyle().set("color", "gray");
            add(projectTitle);

            var grid = new Grid<>(EnumDTO.class);
            grid.removeAllColumns();
            var filterRow = grid.appendHeaderRow();
            var dataProvider = new ListDataProvider<>(items);
            grid.setDataProvider(dataProvider);
            var nameColumn = grid.addColumn(EnumDTO::getName).setHeader("Name");
            var fieldFilter = new TextField();
            fieldFilter.addValueChangeListener(event -> dataProvider.addFilter(
                    enumDTO -> StringUtils.containsIgnoreCase(enumDTO.getName(),
                            fieldFilter.getValue())));
            fieldFilter.setValueChangeMode(ValueChangeMode.EAGER);
            filterRow.getCell(nameColumn).setComponent(fieldFilter);
            fieldFilter.setSizeFull();
            fieldFilter.setPlaceholder("Filter");
            grid.addColumn(EnumDTO::getValue).setHeader("Values");
            grid.addComponentColumn(enumDTO -> {
                var edit = new Button("Edit");
                edit.addClassName("edit");
                edit.addClickListener(e -> edit.getUI().ifPresent(ui ->
                                ui.navigate(EnumsEditPage.class, enumDTO.getId().toString())
                        )
                );
                edit.setEnabled(true);
                return edit;
            });
            add(grid);

        }

    }

}
