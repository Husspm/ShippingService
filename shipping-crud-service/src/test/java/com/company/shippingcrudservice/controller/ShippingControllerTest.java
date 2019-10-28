package com.company.shippingcrudservice.controller;

import com.company.shippingcrudservice.dao.InvoiceItemDao;
import com.company.shippingcrudservice.dao.ShippingDao;
import com.company.shippingcrudservice.model.Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingDao dao;

    @MockBean
    private InvoiceItemDao itemDao;

    private static ObjectMapper mapper = new ObjectMapper();
    private static Invoice inputInvoice, outputInvoice;

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        inputInvoice = new Invoice(1, "20201", LocalDate.now(), BigDecimal.valueOf(10.99), BigDecimal.valueOf(14.99), BigDecimal.valueOf(5.99), new ArrayList<>());
        outputInvoice = new Invoice(1, "20201", LocalDate.now(), BigDecimal.valueOf(10.99), BigDecimal.valueOf(14.99), BigDecimal.valueOf(5.99), new ArrayList<>());
        outputInvoice.setInvoiceId(1);
    }

    private <T> String writeToJson(T obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void getAllInvoiceById() throws Exception {
        when(dao.findById(1)).thenReturn(Optional.ofNullable(outputInvoice));

        mockMvc.perform(get("/invoices/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(writeToJson(outputInvoice)));
    }

    @Test
    public void getAllByCustomerId() throws Exception {
        List<Invoice> allByCustomer = new ArrayList<>();
        allByCustomer.add(outputInvoice);
        when(dao.findAllByCustomerId(1)).thenReturn(allByCustomer);

        mockMvc.perform(get("/invoices/byCustomer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(writeToJson(allByCustomer)));
    }

    @Test
    public void placeAnOrder() throws Exception {
        when(dao.save(inputInvoice)).thenReturn(outputInvoice);
        mockMvc.perform(post("/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(writeToJson(inputInvoice)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(writeToJson(outputInvoice)));
    }
}