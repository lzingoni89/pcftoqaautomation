package com.arrowsoft.pcftoqaautomation.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ImportPCFServiceTest {

    @Autowired
    private ImportPCFService importPCFService;

    @Test
    public void whenImportPCFs_thenSaveOnDatabase() throws ParserConfigurationException, SAXException, IOException {
        var fileName = "sample/pcf/TabBar.pcf";
        var classLoader = ClassLoader.getSystemClassLoader().getResource(fileName);
        if (classLoader == null) {
            return;
        }
        var pcfFile = new File(classLoader.getFile());
        importPCFService.readPCF(pcfFile);

    }

}
