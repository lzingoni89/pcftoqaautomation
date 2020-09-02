package com.arrowsoft.pcftoqaautomation.web.batch;

import com.arrowsoft.pcftoqaautomation.batch.BatchProcessCode;
import com.arrowsoft.pcftoqaautomation.batch.shared.SharedBatchParameterCodes;
import com.arrowsoft.pcftoqaautomation.service.CompanyService;
import com.arrowsoft.pcftoqaautomation.service.JobLauncherService;
import com.arrowsoft.pcftoqaautomation.service.dto.company.CompanyDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.company.ProjectDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.BatchExecutionDTO;
import com.arrowsoft.pcftoqaautomation.service.dto.joblauncher.JobExecutionStatusDTO;
import com.arrowsoft.pcftoqaautomation.web.MainLayout;
import com.arrowsoft.pcftoqaautomation.web.util.MessagesDisplaySource;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;

import java.util.HashMap;

@Log4j2
@Route(value = "batch", layout = MainLayout.class)
@PageTitle("Batch | Guidewire ETL")
public class BatchViewPage extends VerticalLayout {

    private final MessagesDisplaySource displaySource;
    private final JobLauncherService jobLauncherService;
    private final CompanyService companyService;
    private FormLayout batchForm;
    private ComboBox<BatchProcessCode> processCodeComboBox;
    private ComboBox<CompanyDTO> companyDTOComboBox;
    private ComboBox<ProjectDTO> projectDTOComboBox;
    private Button executeBtn;
    private Grid<JobExecutionStatusDTO> jobExecutionGrid;
    private FeederThread thread;

    public BatchViewPage(JobLauncherService jobLauncherService,
                         MessagesDisplaySource displaySource,
                         CompanyService companyService) {
        this.displaySource = displaySource;
        this.jobLauncherService = jobLauncherService;
        this.companyService = companyService;
        createTitle();
        createForm();
        createExecuteBtn();
        createProjectCB();
        createCompanyCB();
        createBatchCB();
        batchForm.add(processCodeComboBox);
        batchForm.add(companyDTOComboBox);
        batchForm.add(projectDTOComboBox);
        add(executeBtn);
        createProjectsTitle();
        createJobGrid();

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        thread = new FeederThread(jobExecutionGrid, jobLauncherService, attachEvent.getUI());
        thread.start();

    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        thread.interrupt();
        thread = null;

    }

    private void createTitle() {
        var title = new H1(this.displaySource.getDisplayValue("navbar.tab.batches.submenu.batches"));
        title.getStyle().set("color", "gray");
        add(title);

    }

    private void createProjectsTitle() {
        var title = new H3();
        title.setText("Running Batches");
        title.getStyle().set("color", "gray");
        add(title);

    }

    private void createForm() {
        batchForm = new FormLayout();
        batchForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        add(batchForm);

    }

    private void createExecuteBtn() {
        executeBtn = new Button("Execute");
        executeBtn.setEnabled(false);
        executeBtn.addClickListener(click -> {
            var batchCode = processCodeComboBox.getValue();
            if (batchCode == null) {
                return;

            }
            var dto = new BatchExecutionDTO();
            dto.setBatchProcessCode(batchCode.getCode());
            populateParameters(dto);
            try {
                jobLauncherService.executeJobByCode(dto);
                executeBtn.setEnabled(false);
                jobExecutionGrid.setItems(jobLauncherService.getWorkingJobs());

            } catch (JobParametersInvalidException
                    | JobInstanceAlreadyExistsException
                    | NoSuchJobException
                    | JobExecutionAlreadyRunningException e) {
                log.error(e.getMessage());
                log.error(e);

            }

        });

    }

    private boolean validToBeExecuted() {
        var batchCode = processCodeComboBox.getValue();
        if (batchCode == null) {
            return false;

        }
        var dto = new BatchExecutionDTO();
        dto.setBatchProcessCode(batchCode.getCode());
        if (batchCode != BatchProcessCode.DATA_ADMIN_LOADER_BATCH && doNotLoadParameters()) {
            return false;

        }
        populateParameters(dto);
        return !jobLauncherService.isJobRunning(dto);

    }

    private boolean doNotLoadParameters() {
        return companyDTOComboBox.getValue() == null
                || projectDTOComboBox.getValue() == null;

    }

    private void populateParameters(BatchExecutionDTO dto) {
        if (doNotLoadParameters()) {
            return;

        }
        var parameters = new HashMap<String, String>();
        parameters.put(SharedBatchParameterCodes.COMPANY_CODE, companyDTOComboBox.getValue().getCodName());
        parameters.put(SharedBatchParameterCodes.MODULE_CODE, projectDTOComboBox.getValue().getModule().toString());
        parameters.put(SharedBatchParameterCodes.VERSION_CODE, projectDTOComboBox.getValue().getVersion().toString());
        dto.setParameters(parameters);

    }

    private void createBatchCB() {
        processCodeComboBox = new ComboBox<>();
        processCodeComboBox.setLabel("Batch");
        processCodeComboBox.setItemLabelGenerator(BatchProcessCode::getName);
        processCodeComboBox.setItems(BatchProcessCode.values());
        processCodeComboBox.addValueChangeListener(event -> {
            validateCBVisibility(event.getValue());
            executeBtn.setEnabled(validToBeExecuted());

        });
        validateCBVisibility(processCodeComboBox.getValue());

    }

    private void validateCBVisibility(BatchProcessCode batchProcessCode) {
        var valid = batchProcessCode != null && batchProcessCode != BatchProcessCode.DATA_ADMIN_LOADER_BATCH;
        companyDTOComboBox.setVisible(valid);
        projectDTOComboBox.setVisible(valid);

    }

    private void createProjectCB() {
        projectDTOComboBox = new ComboBox<>();
        projectDTOComboBox.setLabel("Project");
        projectDTOComboBox.setItemLabelGenerator(ProjectDTO::getShortName);
        projectDTOComboBox.addValueChangeListener(event -> {
            executeBtn.setEnabled(validToBeExecuted());

        });

    }

    private void createCompanyCB() {
        var companyList = companyService.getCompanyList();
        companyDTOComboBox = new ComboBox<>();
        companyDTOComboBox.setLabel("Company");
        companyDTOComboBox.setItemLabelGenerator(CompanyDTO::getName);
        companyDTOComboBox.setItems(companyList);
        companyDTOComboBox.addValueChangeListener(event -> {
            executeBtn.setEnabled(validToBeExecuted());
            var company = event.getValue();
            if (company == null) {
                return;

            }
            projectDTOComboBox.setItems(companyService.getProjectListByCompany(company.getId()));

        });

    }

    private void createJobGrid() {
        jobExecutionGrid = new Grid<>();
        jobExecutionGrid.addColumn(JobExecutionStatusDTO::getJobName).setHeader("Job");
        jobExecutionGrid.addColumn(JobExecutionStatusDTO::getCompany).setHeader("Company");
        jobExecutionGrid.addColumn(JobExecutionStatusDTO::getModule).setHeader("Module");
        jobExecutionGrid.addColumn(JobExecutionStatusDTO::getVersion).setHeader("Version");
        jobExecutionGrid.addColumn(JobExecutionStatusDTO::getStatus).setHeader("Status");
        jobExecutionGrid.addComponentColumn(jobExecution -> {
            var cancelBtn = new Button("Cancel");
            cancelBtn.addClickListener(e -> {
                jobLauncherService.terminateExecution(jobExecution.getId());

            });
            return cancelBtn;

        });
        jobExecutionGrid.setItems(jobLauncherService.getWorkingJobs());

        add(jobExecutionGrid);

    }

    private static class FeederThread extends Thread {
        private final Grid<JobExecutionStatusDTO> jobExecutionGrid;
        private final JobLauncherService jobLauncherService;
        private final UI ui;

        public FeederThread(Grid<JobExecutionStatusDTO> jobExecutionGrid,
                            JobLauncherService jobLauncherService,
                            UI ui) {
            this.jobExecutionGrid = jobExecutionGrid;
            this.jobLauncherService = jobLauncherService;
            this.ui = ui;

        }

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(5000);
                    this.ui.access(() -> jobExecutionGrid.setItems(jobLauncherService.getWorkingJobs()));

                }

            } catch (InterruptedException ignored) {
            }

        }

    }


}
