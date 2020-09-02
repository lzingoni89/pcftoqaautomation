package com.arrowsoft.pcftoqaautomation.web.batch;

import com.arrowsoft.pcftoqaautomation.service.JobLauncherService;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.arrowsoft.pcftoqaautomation.web.util.MessagesDisplaySource;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "history", layout = MainLayout.class)
@PageTitle("Batch History | Guidewire ETL")
public class BatchHistoryPage extends VerticalLayout {

    private final JobLauncherService jobLauncherService;
    private final MessagesDisplaySource displaySource;

    public BatchHistoryPage(JobLauncherService jobLauncherService,
                            MessagesDisplaySource displaySource) {
        this.jobLauncherService = jobLauncherService;
        this.displaySource = displaySource;
        createTitle();
    }

    private void createTitle() {
        var title = new H1(this.displaySource.getDisplayValue("navbar.tab.batches.submenu.history"));
        title.getStyle().set("color", "gray");
        add(title);

    }


}
