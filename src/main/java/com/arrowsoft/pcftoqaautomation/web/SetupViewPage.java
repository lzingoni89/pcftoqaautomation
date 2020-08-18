package com.arrowsoft.pcftoqaautomation.web;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "setup", layout = MainLayout.class)
@PageTitle("Setup | Guidewire ETL")
public class SetupViewPage extends VerticalLayout {

    public SetupViewPage() {
        add(new H1("Setup Page"));

    }

}
