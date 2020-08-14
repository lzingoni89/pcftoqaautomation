package com.arrowsoft.pcftoqaautomation.controller;

import com.arrowsoft.pcftoqaautomation.service.TemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/service")
public class ServiceController {

    private final TemplateService templateService;

    public ServiceController(TemplateService templateService) {
        this.templateService = templateService;

    }

    @GetMapping("/template")
    public void executeTemplateService() throws IOException {
        log.info("executeTemplateService() - Start");
        templateService.useTemplate();

    }

}
