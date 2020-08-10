package com.arrowsoft.pcftoqaautomation.web;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class HomePage extends VerticalLayout {

    public HomePage() {
        add(
                new H1("Super Mega PCF Migration System")
        );

    }

}
