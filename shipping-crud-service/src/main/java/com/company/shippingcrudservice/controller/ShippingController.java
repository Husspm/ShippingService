package com.company.shippingcrudservice.controller;

import com.company.shippingcrudservice.dao.InvoiceItemDao;
import com.company.shippingcrudservice.dao.ShippingDao;
import com.company.shippingcrudservice.model.Invoice;
import com.company.shippingcrudservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/invoices")
public class ShippingController {
    @Autowired
    ShippingDao shippingDao;
    @Autowired
    InvoiceItemDao itemDao;

    @GetMapping("/{id}")
    public Invoice getAllInvoiceById(@PathVariable Integer id) {
        return shippingDao.findById(id).orElseThrow(()-> new NoSuchElementException("No Item found for that Id"));
    }

    @GetMapping("byCustomer/{id}")
    public List<Invoice> getAllByCustomerId(@PathVariable Integer id) {
        return shippingDao.findAllByCustomerId(id);
    }

    @PostMapping
    public Invoice placeAnOrder(@RequestBody @Valid Invoice invoice) {
        List<InvoiceItem> allItemsInOrder = new ArrayList<>();
        invoice.getInvoiceItems().forEach(invoiceItem -> {
            invoiceItem = itemDao.save(invoiceItem);
            allItemsInOrder.add(invoiceItem);
        });

        invoice = shippingDao.save(invoice);
        invoice.setInvoiceItems(allItemsInOrder);
        return invoice;
    }


}
