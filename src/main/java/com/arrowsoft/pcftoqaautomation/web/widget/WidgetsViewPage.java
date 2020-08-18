package com.arrowsoft.pcftoqaautomation.web.widget;

import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "widget", layout = MainLayout.class)
@PageTitle("Widget | Guidewire ETL")
public class WidgetsViewPage extends VerticalLayout {

    public WidgetsViewPage() {
        add(new H1("Widgets Page"));

    }

}
