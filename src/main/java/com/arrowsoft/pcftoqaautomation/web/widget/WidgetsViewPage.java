package com.arrowsoft.pcftoqaautomation.web.widget;

import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.HowFindEnum;
import com.arrowsoft.pcftoqaautomation.enums.WidgetPrefixEnum;
import com.arrowsoft.pcftoqaautomation.service.WidgetService;
import com.arrowsoft.pcftoqaautomation.service.dto.widget.WidgetTypeDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

@Route(value = "widget", layout = MainLayout.class)
@PageTitle("Widget | Guidewire ETL")
public class WidgetsViewPage extends VerticalLayout implements HasUrlParameter<String> {

    private final WidgetService widgetService;
    private final Grid<WidgetTypeDTO> widgetGrid;
    private final Binder<WidgetTypeDTO> widgetBinder;
    private ListDataProvider<WidgetTypeDTO> dataProvider;

    public WidgetsViewPage(WidgetService widgetService) {
        this.widgetService = widgetService;
        this.widgetBinder = new Binder<>();
        this.widgetGrid = createWidgetGrid();
        configEditor();
        add(createTitle());
        add(this.widgetGrid);

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        var versionSelected = GWVersionEnum.VER_10;
        if (!parameter.isBlank()) {
            versionSelected = GWVersionEnum.valueOf(parameter);

        }
        this.dataProvider = new ListDataProvider<>(this.widgetService.getWidgetTypesByVersion(versionSelected));
        widgetGrid.setDataProvider(dataProvider);
        this.dataProvider.clearFilters();

    }

    private H1 createTitle() {
        var title = new H1();
        title.getStyle().set("color", "gray");
        title.setText("Widgets");
        return title;

    }

    private Grid<WidgetTypeDTO> createWidgetGrid() {
        var grid = new Grid<>(WidgetTypeDTO.class);
        grid.getStyle().set("height", "70vh");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.removeAllColumns();
        var filterRow = grid.appendHeaderRow();
        var typeColumn = grid.addColumn(WidgetTypeDTO::getType).setHeader("Widget Type");
        createTextFilter(filterRow, typeColumn);
        var prefixColumn = grid.addColumn(WidgetTypeDTO::getPrefix).setHeader("Prefix");
        createPrefixFilter(filterRow, prefixColumn);
        configPrefixWidgetBinder(prefixColumn);
        var findByColumn = grid.addColumn(WidgetTypeDTO::getFindBy).setHeader("Find By");
        createFindByFilter(filterRow, findByColumn);
        configFindByWidgetBinder(findByColumn);
        var migrateColumn = grid.addColumn(WidgetTypeDTO::getMigrateView).setHeader("Migrate");
        createMigrateFilter(filterRow, migrateColumn);

        return grid;

    }

    private void configEditor() {
        Editor<WidgetTypeDTO> editor = this.widgetGrid.getEditor();
        editor.setBinder(this.widgetBinder);
        editor.setBuffered(true);

        Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());
        var editorColumn = this.widgetGrid.addComponentColumn(widget -> {
            var edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> editor.editItem(widget));
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;

        });

        editor.addOpenListener(e -> editButtons.forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.forEach(button -> button.setEnabled(!editor.isOpen())));

        var save = new Button("Save", e -> {
            editor.save();
            this.widgetGrid.getDataProvider().refreshAll();

        });
        save.addClassName("save");
        var cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");
        this.widgetGrid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        var buttons = new HorizontalLayout(save, cancel);
        editorColumn.setEditorComponent(buttons);
        editor.addSaveListener(event -> {
            this.widgetService.saveWidgetType(event.getItem());

        });

    }

    private void createTextFilter(HeaderRow filterRow, Grid.Column<WidgetTypeDTO> typeColumn) {
        var fieldFilter = new TextField();
        fieldFilter.addValueChangeListener(event -> dataProvider.addFilter(
                widget -> StringUtils.containsIgnoreCase(widget.getTypeView(),
                        fieldFilter.getValue())));
        fieldFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(typeColumn).setComponent(fieldFilter);
        fieldFilter.setSizeFull();
        fieldFilter.setPlaceholder("Filter");

    }

    private void createPrefixFilter(HeaderRow filterRow, Grid.Column<WidgetTypeDTO> typeColumn) {
        var comboFilter = new ComboBox<String>();
        var items = Arrays.stream(WidgetPrefixEnum.values()).map(WidgetPrefixEnum::getPrefix).collect(Collectors.toList());
        comboFilter.setItems(items);
        comboFilter.addValueChangeListener(event -> dataProvider.addFilter(
                widget -> comboFilter.getValue() == null || widget.getPrefix().getPrefix().equals(comboFilter.getValue())));
        filterRow.getCell(typeColumn).setComponent(comboFilter);
        comboFilter.setSizeFull();
        comboFilter.setPlaceholder("Filter");

    }

    private void createFindByFilter(HeaderRow filterRow, Grid.Column<WidgetTypeDTO> typeColumn) {
        var comboFilter = new ComboBox<String>();
        var items = Arrays.stream(HowFindEnum.values()).map(HowFindEnum::getHow).collect(Collectors.toList());
        comboFilter.setItems(items);
        comboFilter.addValueChangeListener(event -> dataProvider.addFilter(
                widget -> comboFilter.getValue() == null || widget.getFindBy().getHow().equals(comboFilter.getValue())));
        filterRow.getCell(typeColumn).setComponent(comboFilter);
        comboFilter.setSizeFull();
        comboFilter.setPlaceholder("Filter");

    }

    private void createMigrateFilter(HeaderRow filterRow, Grid.Column<WidgetTypeDTO> typeColumn) {
        var fieldFilter = new Checkbox();
        fieldFilter.addValueChangeListener(event -> dataProvider.addFilter(
                widget -> widget.getMigrateView().isOriginalValue() == fieldFilter.getValue()));
        filterRow.getCell(typeColumn).setComponent(fieldFilter);
        fieldFilter.setSizeFull();

    }

    private void configPrefixWidgetBinder(Grid.Column<WidgetTypeDTO> prefixColumn) {
        var prefixSelect = new Select<WidgetPrefixEnum>();
        prefixSelect.setItems(WidgetPrefixEnum.values());
        this.widgetBinder.forField(prefixSelect).bind(WidgetTypeDTO::getPrefix, WidgetTypeDTO::setPrefix);
        prefixColumn.setEditorComponent(prefixSelect);

    }

    private void configFindByWidgetBinder(Grid.Column<WidgetTypeDTO> findByColumn) {
        var findBySelect = new Select<HowFindEnum>();
        findBySelect.setItems(HowFindEnum.values());
        this.widgetBinder.forField(findBySelect).bind(WidgetTypeDTO::getFindBy, WidgetTypeDTO::setFindBy);
        findByColumn.setEditorComponent(findBySelect);

    }

}
