package com.arrowsoft.pcftoqaautomation.web;

import com.arrowsoft.pcftoqaautomation.enums.GWVersionEnum;
import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.web.batch.BatchHistoryPage;
import com.arrowsoft.pcftoqaautomation.web.batch.BatchViewPage;
import com.arrowsoft.pcftoqaautomation.web.company.CompanyViewPage;
import com.arrowsoft.pcftoqaautomation.web.enums.EnumsViewPage;
import com.arrowsoft.pcftoqaautomation.web.setup.SetupViewPage;
import com.arrowsoft.pcftoqaautomation.web.util.MessagesDisplaySource;
import com.arrowsoft.pcftoqaautomation.web.widget.WidgetsViewPage;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Push
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainLayout extends AppLayout implements RouterLayout {

    private final MessagesDisplaySource displaySource;
    private final CompanyService companyService;
    private final Tabs drawerTabs;
    private final Tab companyTab;
    private final Tab widgetTab;
    private final Tab setupTab;
    private final Tab enumTab;
    private final Tab batchTab;

    public MainLayout(MessagesDisplaySource displaySource,
                      CompanyService companyService) {
        this.displaySource = displaySource;
        this.companyService = companyService;
        this.companyTab = createCompanyTab();
        this.widgetTab = createWidgetTab();
        this.setupTab = createSetupTab();
        this.enumTab = createEnumTab();
        this.batchTab = createBatchesTab();
        this.drawerTabs = createDrawerTabs();
        setPrimarySection(AppLayout.Section.DRAWER);
        createNavbar();
        populateDrawer();

    }

    private void createNavbar() {
        var tabsNavbar = new Tabs(companyTab, widgetTab, enumTab, batchTab, setupTab);
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

    private Tab createEnumTab() {
        return new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.enum.display"), EnumsViewPage.class));

    }

    private Tab createBatchesTab() {
        return new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.batches.display"), BatchViewPage.class));

    }

    private void populateDrawer() {
        drawerTabs.removeAll();
        if (companyTab.isSelected()) {
            for (var company : companyService.getCompanyList()) {
                drawerTabs.add(new Tab(new RouterLink(company.getName(), CompanyViewPage.class, company.getId().toString())));

            }

        }
        if (widgetTab.isSelected()) {
            for (var version : GWVersionEnum.values()) {
                drawerTabs.add(new Tab(new RouterLink("Version " + version.getDesc(), WidgetsViewPage.class, version.getCode())));

            }

        }
        if (enumTab.isSelected()) {
            for (var company : companyService.getCompanyList()) {
                drawerTabs.add(new Tab(new RouterLink(company.getName(), EnumsViewPage.class, company.getId().toString())));

            }

        }
        if (batchTab.isSelected()) {
            drawerTabs.add(new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.batches.submenu.batches"), BatchViewPage.class)));
            drawerTabs.add(new Tab(new RouterLink(this.displaySource.getDisplayValue("navbar.tab.batches.submenu.history"), BatchHistoryPage.class)));

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
