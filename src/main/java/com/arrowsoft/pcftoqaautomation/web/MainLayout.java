package com.arrowsoft.pcftoqaautomation.web;

import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.dto.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.web.util.MessagesDisplaySource;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainLayout extends AppLayout implements RouterLayout {

    private final MessagesDisplaySource displaySource;
    private final CompanyService companyService;
    private final Tabs drawerTabs;
    private final Tab companyTab;
    private final Tab widgetTab;
    private final Tab setupTab;

    public MainLayout(MessagesDisplaySource displaySource,
                      CompanyService companyService) {
        this.displaySource = displaySource;
        this.companyService = companyService;
        this.companyTab = createCompanyTab();
        this.widgetTab = createWidgetTab();
        this.setupTab = createSetupTab();
        this.drawerTabs = createDrawerTabs();
        setPrimarySection(AppLayout.Section.DRAWER);
        createNavbar();
        populateDrawer();

    }

    private void createNavbar() {
        var tabsNavbar = new Tabs(companyTab, widgetTab, setupTab);
        tabsNavbar.addSelectedChangeListener(selectedChangeEvent -> {
            populateDrawer();

        });
        var logo = new Image("img/logo-gw.png", "Guidewire");
        logo.setWidth("150px");
        addToNavbar(new DrawerToggle(), logo, tabsNavbar);

    }

    private Tab createCompanyTab() {
        return new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.company.display"), CompanyViewPage.class));

    }

    private Tab createWidgetTab() {
        return new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.widgets.display"), WidgetsViewPage.class));

    }

    private Tab createSetupTab() {
        return new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.setup.display"), SetupViewPage.class));

    }

    private void populateDrawer() {
        drawerTabs.removeAll();
        if (companyTab.isSelected()) {
            for (CompanyDTO company : companyService.getCompanyList()) {
                drawerTabs.add(new Tab(company.getName()));

            }

        }
        if (widgetTab.isSelected()) {
            drawerTabs.add(new Tab("lalala 1"));
            drawerTabs.add(new Tab("lalala 2"));

        }
        if (setupTab.isSelected()) {
            drawerTabs.add(new Tab("zxczxc 1"));
            drawerTabs.add(new Tab("zxczxc 2"));

        }

    }

    private Tabs createDrawerTabs() {
        var logoSBI = new Image("img/sbi.png", "SBI-Technology");
        logoSBI.setWidth("150px");
        var tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(logoSBI, tabs);
        return tabs;

    }

}
