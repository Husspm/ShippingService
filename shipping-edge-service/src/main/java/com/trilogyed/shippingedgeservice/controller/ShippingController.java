package com.trilogyed.shippingedgeservice.controller;

import com.trilogyed.shippingedgeservice.model.Invoice;
import com.trilogyed.shippingedgeservice.service.ShippingService;
import com.trilogyed.shippingedgeservice.util.feign.InvoiceClient;
import com.trilogyed.shippingedgeservice.viewmodel.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ShippingController {
    @Autowired
    private ShippingService service;
    @Autowired
    private InvoiceClient client;

    @RequestMapping("/ship")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice ship(@RequestBody @Valid Order order) {
        return service.ship(order);
    }

    @RequestMapping("/invoice/customer/{customerId}")
    public List<Invoice> getInvoicesByCustomerId(@PathVariable Integer customerId) {
        return client.getByCustomerId(customerId);
    }
}
