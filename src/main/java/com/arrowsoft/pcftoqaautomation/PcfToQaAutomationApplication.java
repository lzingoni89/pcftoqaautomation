package com.arrowsoft.pcftoqaautomation;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class PcfToQaAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcfToQaAutomationApplication.class, args);
    }

}
