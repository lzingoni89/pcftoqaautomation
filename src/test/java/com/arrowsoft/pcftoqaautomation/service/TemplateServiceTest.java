package com.arrowsoft.pcftoqaautomation.service;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {"test"})
class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Test
    void useTemplate() throws IOException, TemplateException {
        templateService.useTemplate();
        Assert.isTrue(templateService != null, "Es true locoooooooo");

    }

}