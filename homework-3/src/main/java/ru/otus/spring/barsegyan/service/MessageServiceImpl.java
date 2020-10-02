package ru.otus.spring.barsegyan.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.config.AppProps;

@Service
public class MessageServiceImpl implements MessageService {

    private final AppProps appProps;
    private final MessageSource messageSource;

    public MessageServiceImpl(AppProps appProps, MessageSource messageSource) {
        this.appProps = appProps;
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(String code) {
        return messageSource.getMessage(code, new Object[]{}, appProps.getLocale());
    }

    public String getLocalizedMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, appProps.getLocale());
    }
}
