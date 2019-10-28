package com.trilogyed.shippingedgeservice.service;

import com.trilogyed.shippingedgeservice.model.Invoice;
import com.trilogyed.shippingedgeservice.model.InvoiceItem;
import com.trilogyed.shippingedgeservice.util.feign.InvoiceClient;
import com.trilogyed.shippingedgeservice.viewmodel.Item;
import com.trilogyed.shippingedgeservice.viewmodel.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    private final static BigDecimal SALES_TAX_RATE = new BigDecimal(".072");

    @Autowired
    private InvoiceClient client;

    public Invoice ship(Order order) {
        BigDecimal shippingCostPerItem = getShippingCost(order.getShiptoZip());
        List<InvoiceItem> invoiceItems = order.getItems().stream()
                .map(i -> new InvoiceItem(
                        null,
                        null,
                        i.getName(),
                        i.getDescription(),
                        i.getWeight(),
                        shippingCostPerItem
                )).collect(Collectors.toList());
        BigDecimal surcharge = getSurcharge(order.getItems());
        BigDecimal subTotal = shippingCostPerItem
                .multiply(new BigDecimal(invoiceItems.size()))
                .add(surcharge);
        BigDecimal salesTax = subTotal
                .multiply(SALES_TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subTotal.add(salesTax);
        Invoice invoice = new Invoice(
                null,
                order.getCustomerId(),
                order.getShiptoZip(),
                LocalDate.now(),
                total,
                salesTax,
                surcharge,
                invoiceItems
        );
        return client.create(invoice);
    }

    private BigDecimal getShippingCost(String zip) {
        String val = "";
        if (zip.matches("^[012]\\d*")) {
            val = "9.99";
        } else if (zip.matches("^3\\d*")) {
            val = "14.99";
        } else if (zip.matches(("^[456]\\d*"))) {
            val = "19.99";
        } else {
            val = "24.99";
        }
        return new BigDecimal(val);
    }

    private BigDecimal getSurcharge(List<Item> items) {
        Integer totalWeight = items.stream()
                .map(Item::getWeight)
                .reduce(Integer::sum)
                .get();
        String val;
        if (totalWeight > 10 && totalWeight <= 17) {
            val = "8.50";
        } else if (totalWeight <= 25) {
            val = "12.50";
        } else if (totalWeight <= 35) {
            val = "19.99";
        } else {
            val = "50.00";
        }
        return new BigDecimal(val);
    }
}
