package com.arrowsoft.pcftoqaautomation.service;

import com.arrowsoft.pcftoqaautomation.repository.PCFRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

@Service
@Log4j2
public class TemplateService {

    private final PCFRepository pcfRepository;

    public TemplateService(PCFRepository pcfRepository) {
        this.pcfRepository = pcfRepository;
    }

    public void useTemplate() throws IOException {
        var cfg = new Configuration(Configuration.VERSION_2_3_29);
        var classLoader = ClassLoader.getSystemClassLoader().getResource("templates");
        if (classLoader == null) {
            log.info("Container element not found");
            return;
        }
        cfg.setDirectoryForTemplateLoading(new File(classLoader.getFile()));
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

        // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        cfg.setWrapUncheckedExceptions(false);

        // Do not fall back to higher scopes when reading a null loop variable:
        cfg.setFallbackOnNullLoopVariable(false);

        var temp = cfg.getTemplate("test.ftlh");
        var root = new HashMap<>();
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("sample/result/TabBarElements.cs"))) {
            var pcfResult = pcfRepository.findById(20L);
            if (pcfResult.isEmpty()) {
                log.info("PCF NOT FOUND");
                return;

            }
            var pcf = pcfResult.get();
            root.put("pcf", pcf.getPcfName().substring(0, pcf.getPcfName().indexOf(".pcf")));
            temp.process(root, out);

        } catch (Exception e) {
            log.error(e);

        }

    }

}
