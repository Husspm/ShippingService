package com.trilogyed.shippingedgeservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.shippingedgeservice.model.Invoice;
import com.trilogyed.shippingedgeservice.model.InvoiceItem;
import com.trilogyed.shippingedgeservice.service.ShippingService;
import com.trilogyed.shippingedgeservice.util.feign.InvoiceClient;
import com.trilogyed.shippingedgeservice.viewmodel.Item;
import com.trilogyed.shippingedgeservice.viewmodel.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ShippingController.class)
public class ShippingControllerTest {
    @MockBean
    private ShippingService service;
    @MockBean
    private InvoiceClient client;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private boolean init = false;

    private List<Item> items;
    private Order order;
    private Invoice invoice;

    private void initializeData() {
        items = Arrays.asList(
                new Item(
                        "name",
                        "description",
                        12
                ),
                new Item(
                        "name 2",
                        "description 2",
                        20
                )
        );
        order = new Order(
                1,
                "30076",
                items
        );
        invoice = new Invoice(
                1,
                1,
                "30076",
                LocalDate.now(),
                new BigDecimal("53.57"),
                new BigDecimal("3.66"),
                new BigDecimal("19.99"),
                items.stream()
                        .map(i -> new InvoiceItem(
                                items.indexOf(i) + 1,
                                1,
                                i.getName(),
                                i.getDescription(),
                                i.getWeight(),
                                new BigDecimal("14.99")
                        )).collect(Collectors.toList())
        );
    }

    @Before
    public void setUp() {
        reset(service, client);
        if (!init) {
            initializeData();
        }
    }

    @Test
    public void ship_withValidArgs_shouldReturnStatus201WithInvoice() throws Exception {
        when(service.ship(order)).thenReturn(invoice);
        String inputJson = mapper.writeValueAsString(order);
        String expectedJson = mapper.writeValueAsString(invoice);

        mockMvc.perform(post("/ship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void ship_withInvalidArgs_shouldReturnStatus422WithVndErrors() throws Exception {
        Order order = clone(this.order, Order.class);
        order.setCustomerId(-4);
        order.getItems().get(0).setName(null);
        String inputJson = mapper.writeValueAsString(order);

        mockMvc.perform(post("/ship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    public void getByCustomerId_shouldReturnStatus200WithInvoiceList() throws Exception {
        List<Invoice> invoiceList = Collections.singletonList(invoice);
        when(client.getByCustomerId(invoice.getCustomerId()))
                .thenReturn(invoiceList);
        String expectedJson = mapper.writeValueAsString(invoiceList);

        mockMvc.perform(get("/invoice/customer/" + invoice.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    private <T> T clone(T object, Class<T> clazz) throws IOException {
        return mapper.readValue(mapper.writeValueAsString(object), clazz);
    }
}