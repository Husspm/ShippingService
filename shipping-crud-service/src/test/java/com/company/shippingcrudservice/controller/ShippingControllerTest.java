package com.company.shippingcrudservice.controller;

import com.company.shippingcrudservice.dao.InvoiceItemDao;
import com.company.shippingcrudservice.dao.ShippingDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@WebMvcTest
public class ShippingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShippingDao dao;

    @MockBean
    InvoiceItemDao itemDao;

    private <T> String writeToJson(T obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void getAllInvoiceById() {
    }

    @Test
    public void getAllByCustomerId() {
    }

    @Test
    public void placeAnOrder() {
    }
}