package com.trilogyed.shippingedgeservice.service;

import com.trilogyed.shippingedgeservice.AbstractTest;
import com.trilogyed.shippingedgeservice.ShippingEdgeServiceApplicationTests;
import com.trilogyed.shippingedgeservice.model.Invoice;
import com.trilogyed.shippingedgeservice.model.InvoiceItem;
import com.trilogyed.shippingedgeservice.util.feign.InvoiceClient;
import com.trilogyed.shippingedgeservice.viewmodel.Item;
import com.trilogyed.shippingedgeservice.viewmodel.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShippingEdgeServiceApplicationTests.class})
public class ShippingServiceTest extends AbstractTest {
    @InjectMocks
    private ShippingService service;
    @Mock
    private InvoiceClient client;

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
                new BigDecimal("3.60"),
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
        reset(client);
        if (!init) {
            initializeData();
        }
    }

    @Test
    public void ship_shouldCalculateCostsAndReturnInvoice() throws IOException {
        Invoice invoiceNoId = clone(invoice, Invoice.class);
        invoiceNoId.setInvoiceId(null);
        invoiceNoId.getInvoiceItems().forEach(i -> {
            i.setInvoiceItemId(null);
            i.setInvoiceId(null);
        });
        when(client.create(invoiceNoId)).thenReturn(invoice);

        Invoice actual = service.ship(order);

        assertEquals(invoice, actual);
    }
}