package com.trilogyed.shippingedgeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class AbstractTest {
    @Autowired
    protected ObjectMapper mapper;

    protected <T> T clone(T object, Class<T> clazz) throws IOException {
        return mapper.readValue(mapper.writeValueAsString(object), clazz);
    }
}
