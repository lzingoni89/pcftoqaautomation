package com.arrowsoft.pcftoqaautomation.web;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainLayout extends AppLayout implements RouterLayout {

    public MainLayout() {
        setPrimarySection(AppLayout.Section.DRAWER);
        createNavbar();
        createDrawer();

    }

    private void createNavbar() {
        var tabsNavbar = new Tabs(new Tab("Companies"), new Tab("Widgets"), new Tab("About me"));
        var logo = new Image("img/logo-gw.png", "Guidewire");
        logo.setWidth("150px");
        addToNavbar(new DrawerToggle(), logo, tabsNavbar);

    }

    private void createDrawer() {
        var tabs = new Tabs(new Tab("Home"), new Tab("About"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);

    }

}
