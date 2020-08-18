package com.arrowsoft.pcftoqaautomation.web.company;

import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.dto.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@Route(value = "company", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Company | Guidewire ETL")
public class CompanyViewPage extends VerticalLayout implements HasUrlParameter<String> {

    private final CompanyService companyService;
    private final FormLayout form;
    private final H1 title;
    private final TextField companyNameField;
    private final TextField codeNamespaceField;

    public CompanyViewPage(CompanyService companyService) {
        this.companyService = companyService;
        this.title = new H1();
        this.companyNameField = new TextField("Name");
        this.codeNamespaceField = new TextField("Code Namespace");
        this.form = createForm();
        add(this.form);

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        if (parameter.isEmpty()) {
            this.title.setText("EMPTY");
            this.form.removeAll();
            return;

        }
        var dto = companyService.findCompanyByID(parameter);
        this.title.setText(dto.getName());
        populateForm(dto);

    }

    private FormLayout createForm() {
        var form = new FormLayout();
        form.add(companyNameField, codeNamespaceField);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        add(this.title);
        return form;

    }

    private void populateForm(CompanyDTO dto) {
        if(form.getChildren().count() == 0) {
            form.add(companyNameField, codeNamespaceField);

        }
        companyNameField.setValue(dto.getName());
        codeNamespaceField.setValue(dto.getCodNamespace());

    }

}
