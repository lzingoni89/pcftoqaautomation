package com.arrowsoft.pcftoqaautomation.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImportPCFServiceTest {

    @Autowired
    private ImportPCFService importPCFService;

    @Test
    public void whenImportPCFs_thenSaveOnDatabase() throws ParserConfigurationException, SAXException, IOException {
        var fileName = "pcf";
        var classLoader = ClassLoader.getSystemClassLoader().getResource(fileName);
        log.info("No se encontró classLoader papi");
        if (classLoader == null) {
            return;
        }
        var pcfFolder = new File(classLoader.getFile());
        var files = FileUtils.iterateFiles(pcfFolder, new String[]{"pcf"}, true);
        if (!files.hasNext()) {
            log.info("No se encontró nada papi");
            return;
        }
        while (files.hasNext()) {
            importPCFService.readPCF(files.next());
        }

    }

}
