package com.arrowsoft.pcftoqaautomation.web.company;

import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.enums.ModuleEnum;
import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.GitRepoDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.ProjectDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "company", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Company | Guidewire ETL")
public class CompanyViewPage extends VerticalLayout implements HasUrlParameter<String> {

    private final CompanyService companyService;
    private final FormLayout companyForm;
    private final FormLayout projectForm;
    private final H1 title;
    private final H3 projectTitle;

    private final TextField companyNameField;
    private final TextField companyCodeNamespaceField;
    private final Checkbox companyRetiredField;

    private final ComboBox<ModuleEnum> projectModuleField;
    private final ComboBox<GWVersionEnum> projectVersionField;
    private final TextField projectRootPath;
    private final Checkbox projectAdminGitRepository;
    private final ComboBox<GitRepoDTO> projectGitRepo;

    private final Grid<ProjectDTO> projectGrid;
    private final HorizontalLayout actions;
    private final Binder<CompanyDTO> companyBinder;
    private final Binder<ProjectDTO> projectBinder;

    public CompanyViewPage(CompanyService companyService) {
        this.companyService = companyService;
        this.title = createTitle();
        this.projectTitle = createProjectsTitle();

        this.companyNameField = createTextField("Name", true, false);
        this.companyCodeNamespaceField = createTextField("Code Namespace", false, true);
        this.companyRetiredField = createCheckBox("Retired");

        this.projectModuleField = createModuleField();
        this.projectVersionField = createVersionField();
        this.projectRootPath = createTextField("Root Path", false, true);
        this.projectAdminGitRepository = createCheckBox("Admin Git Repository");
        this.projectGitRepo = createGitRepoField();

        this.companyBinder = configCompanyBinder();
        this.projectBinder = configProjectBinder();

        this.projectGrid = createProjectGrid();
        this.actions = createActionBar();
        this.companyForm = createCompanyForm();
        this.projectForm = createProjectForm();
        add(this.title);
        add(this.companyForm);
        add(this.projectTitle);
        add(this.projectGrid);
        add(this.projectForm);
        add(this.actions);

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        this.projectForm.setVisible(false);
        if (parameter.isBlank()) {
            parameter = "1";

        }
        var dto = companyService.findCompanyByID(parameter);
        this.title.setText(dto.getName());
        setVisibleComponents();
        populateForm(dto);

    }

    private void setVisibleComponents() {
        this.actions.setVisible(true);
        this.projectGrid.setVisible(true);
        this.projectTitle.setVisible(true);

    }

    private Binder<CompanyDTO> configCompanyBinder() {
        var binder = new Binder<CompanyDTO>();
        binder.bind(companyNameField, CompanyDTO::getName, CompanyDTO::setName);
        binder.forField(companyCodeNamespaceField)
                .withValidator(new StringLengthValidator(
                        "Please complete the Code Namespace field", 1, null))
                .bind(CompanyDTO::getCodNamespace, CompanyDTO::setCodNamespace);
        binder.bind(companyRetiredField, CompanyDTO::isRetired, CompanyDTO::setRetired);
        return binder;

    }

    private Binder<ProjectDTO> configProjectBinder() {
        var binder = new Binder<ProjectDTO>();
        binder.bind(projectModuleField, ProjectDTO::getModule, ProjectDTO::setModule);
        binder.bind(projectVersionField, ProjectDTO::getVersion, ProjectDTO::setVersion);
        binder.bind(projectGitRepo, ProjectDTO::getSelectedBranch, ProjectDTO::setSelectedBranch);
        binder.forField(projectRootPath)
                .withValidator(new StringLengthValidator(
                        "Please complete the Root Path field", 1, null))
                .bind(ProjectDTO::getRootPath, ProjectDTO::setRootPath);
        binder.bind(projectAdminGitRepository, ProjectDTO::isAdminGitRepository, ProjectDTO::setAdminGitRepository);
        return binder;

    }

    private H1 createTitle() {
        var title = new H1();
        title.getStyle().set("color", "gray");
        return title;

    }

    private H3 createProjectsTitle() {
        var title = new H3();
        title.setText("Projects");
        title.getStyle().set("color", "gray");
        return title;

    }

    private TextField createTextField(String label, boolean readOnly, boolean required) {
        var textField = new TextField(label);
        textField.setReadOnly(readOnly);
        textField.setRequired(required);
        textField.setRequiredIndicatorVisible(required);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        return textField;

    }

    private ComboBox<ModuleEnum> createModuleField() {
        var comboBox = new ComboBox<ModuleEnum>();
        comboBox.setLabel("Module");
        List<ModuleEnum> moduleList = Arrays.asList(ModuleEnum.values().clone());
        comboBox.setItemLabelGenerator(ModuleEnum::getDesc);
        comboBox.setItems(moduleList);
        return comboBox;

    }

    private ComboBox<GWVersionEnum> createVersionField() {
        var comboBox = new ComboBox<GWVersionEnum>();
        comboBox.setLabel("Version");
        List<GWVersionEnum> versionList = Arrays.asList(GWVersionEnum.values().clone());
        comboBox.setItemLabelGenerator(GWVersionEnum::getDesc);
        comboBox.setItems(versionList);
        return comboBox;

    }

    private ComboBox<GitRepoDTO> createGitRepoField() {
        var comboBox = new ComboBox<GitRepoDTO>();
        comboBox.setLabel("Git Branch Selected");
        comboBox.setItemLabelGenerator(GitRepoDTO::getBranchView);
        return comboBox;

    }

    private Checkbox createCheckBox(String label) {
        var checkBox = new Checkbox(label);
        checkBox.setReadOnly(false);
        return checkBox;

    }

    private FormLayout createCompanyForm() {
        var form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        fillCompanyForm(form);
        return form;

    }

    private FormLayout createProjectForm() {
        var form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        fillProjectForm(form);
        form.setVisible(false);
        return form;

    }

    private void fillCompanyForm(FormLayout form) {
        form.add(companyNameField, companyCodeNamespaceField, companyRetiredField);

    }

    private void fillProjectForm(FormLayout form) {
        form.add(projectModuleField, projectVersionField);
        form.add(projectAdminGitRepository, 3);
        form.add(projectRootPath, projectGitRepo);

    }

    private Grid<ProjectDTO> createProjectGrid() {
        var grid = new Grid<>(ProjectDTO.class);
        grid.removeAllColumns();
        grid.addColumn(ProjectDTO::getModuleView).setHeader("Module");
        grid.addColumn(ProjectDTO::getVersionView).setHeader("Version");
        grid.addColumn(ProjectDTO::getRootPath).setHeader("Root Path");
        grid.addColumn(ProjectDTO::getAdminGitRepositoryView).setHeader("Admin Git Repository");
        grid.addColumn(ProjectDTO::getGitBranchRepo).setHeader("Branch selected");
        grid.asSingleSelect().addValueChangeListener(event -> {
            var projectDTO = event.getValue();
            if (projectDTO != null && projectDTO.isAdminGitRepository()) {
                if (projectDTO.getSelectedBranch() != null) {
                    this.projectGitRepo.setItems(projectDTO.getRemoteRepositories());

                }

            }
            this.projectBinder.setBean(projectDTO);
            this.projectForm.setVisible(true);

        });
        return grid;

    }

    private HorizontalLayout createActionBar() {
        var save = new Button("Save");
        save.getStyle().set("marginRight", "10px");
        save.addClickListener(event -> {
            if (updateCompany()) {
                createOrUpdateProject();
                var dto = companyService.findCompanyByID(companyBinder.getBean().getId().toString());
                populateForm(dto);
                projectForm.setVisible(false);

            }

        });
        var reset = new Button("Reset");
        var actionBar = new HorizontalLayout();
        actionBar.add(save, reset);
        return actionBar;

    }

    private boolean updateCompany() {
        if (!companyBinder.writeBeanIfValid(companyBinder.getBean())) {
            BinderValidationStatus<CompanyDTO> validate = companyBinder.validate();
            List<String> errorTextList = validate.getFieldValidationStatuses()
                    .stream().filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(Optional::get).distinct()
                    .collect(Collectors.toList());
            for (String errorText : errorTextList) {
                showNotification(errorText, NotificationVariant.LUMO_ERROR);

            }
            return false;

        }
        companyService.updateCompany(companyBinder.getBean());
        showNotification("Company saved correctly", NotificationVariant.LUMO_SUCCESS);
        return true;

    }

    private void createOrUpdateProject() {
        var projectDTO = projectBinder.getBean();
        if (projectDTO == null) {
            return;

        }
        if (projectBinder.writeBeanIfValid(projectDTO)) {
            companyService.createOrUpdateProject(companyBinder.getBean(), projectDTO);
            showNotification("Project saved correctly", NotificationVariant.LUMO_SUCCESS);

        } else {
            BinderValidationStatus<ProjectDTO> validate = projectBinder.validate();
            List<String> errorTextList = validate.getFieldValidationStatuses()
                    .stream().filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(Optional::get).distinct()
                    .collect(Collectors.toList());
            for (String errorText : errorTextList) {
                showNotification(errorText, NotificationVariant.LUMO_ERROR);

            }

        }

    }

    private void populateForm(CompanyDTO dto) {
        if (companyForm.getChildren().count() == 0) {
            fillCompanyForm(companyForm);

        }
        companyBinder.setBean(dto);
        projectGrid.setItems(dto.getProjectList());

    }

    private void showNotification(String text, NotificationVariant theme) {
        var notification = new Notification(
                "", 3000,
                Notification.Position.BOTTOM_END);
        notification.setText(text);
        notification.setThemeName(theme.getVariantName());
        notification.open();

    }

}
