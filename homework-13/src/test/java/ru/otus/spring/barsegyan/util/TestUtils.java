package ru.otus.spring.barsegyan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class TestUtils {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T parseResponse(MvcResult result, TypeReference<T> valueTypeRef) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), valueTypeRef);
    }
}
