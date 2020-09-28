package ru.otus.spring.barsegyan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    private String questionsFileNameEn;
    private String questionsFileNameRu;
    private Locale locale;

    public String getQuestionsFileNameRu() {
        return questionsFileNameRu;
    }

    public String getQuestionsFileNameEn() {
        return questionsFileNameEn;
    }

    public void setQuestionsFileNameRu(String getQuestionsFileNameRu) {
        this.questionsFileNameRu = getQuestionsFileNameRu;
    }

    public void setQuestionsFileNameEn(String questionsFileNameEn) {
        this.questionsFileNameEn = questionsFileNameEn;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
