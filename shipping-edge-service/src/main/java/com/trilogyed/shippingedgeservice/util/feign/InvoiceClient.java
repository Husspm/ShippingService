package com.trilogyed.shippingedgeservice.util.feign;

import com.trilogyed.shippingedgeservice.model.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "shipping-crud-service", decode404 = true)
public interface InvoiceClient {
    @PostMapping("/invoice")
    Invoice create(@RequestBody Invoice invoice);
    @GetMapping("/invoice/{id}")
    Optional<Invoice> getById(@PathVariable Integer id);
    @GetMapping("/invoice/customer/{customerId}")
    List<Invoice> getByCustomerId(@PathVariable Integer customerId);
}
