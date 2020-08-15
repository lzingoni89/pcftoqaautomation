package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.batch.shared.TemplateBatchUtil;
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
class TemplateBatchUtilTest {

    @Autowired
    private TemplateBatchUtil templateBatchUtil;

    @Test
    void useTemplate() throws IOException, TemplateException {
        templateBatchUtil.useTemplate();
        Assert.isTrue(templateBatchUtil != null, "Es true locoooooooo");

    }

}