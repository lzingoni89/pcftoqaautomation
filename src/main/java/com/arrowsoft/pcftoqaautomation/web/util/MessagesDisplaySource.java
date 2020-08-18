package com.arrowsoft.pcftoqaautomation.web.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessagesDisplaySource {

    private final MessageSource messageSource;

    public MessagesDisplaySource(MessageSource messageSource) {
        this.messageSource = messageSource;

    }

    public String getDisplayValue(String displayKey) {
        return messageSource.getMessage(displayKey, null, LocaleContextHolder.getLocale());

    }

    public String getDisplayValue(String displayKey, String... param) {
        return messageSource.getMessage(displayKey, param, LocaleContextHolder.getLocale());

    }

}
