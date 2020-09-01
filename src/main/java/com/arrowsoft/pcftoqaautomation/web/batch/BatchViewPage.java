package com.arrowsoft.pcftoqaautomation.web.batch;

import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "batch", layout = MainLayout.class)
@PageTitle("Batches | Guidewire ETL")
public class BatchViewPage extends VerticalLayout {

    public BatchViewPage() {
        add(new H1("Batches"));
    }

}
