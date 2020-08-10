package com.arrowsoft.pcftoqaautomation.controller;

import com.arrowsoft.pcftoqaautomation.service.ImportPCFService;
import com.arrowsoft.pcftoqaautomation.service.TemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/service")
public class ServiceController {

    private final ImportPCFService importPCFService;
    private final TemplateService templateService;

    public ServiceController(ImportPCFService importPCFService, TemplateService templateService) {
        this.importPCFService = importPCFService;
        this.templateService = templateService;

    }

    @GetMapping("/import")
    public void executeImportPCFService() throws ParserConfigurationException, SAXException, IOException {
        log.info("executeImportPCFService() - Start");
        var pcfFile = new File("sample/pcf/TabBar.pcf");
        if (!pcfFile.exists()) {
            log.info("File not found");
            return;
        }
        importPCFService.readPCF(pcfFile);

    }

    @GetMapping("/template")
    public void executeTemplateService() throws IOException {
        log.info("executeTemplateService() - Start");
        templateService.useTemplate();

    }

}
