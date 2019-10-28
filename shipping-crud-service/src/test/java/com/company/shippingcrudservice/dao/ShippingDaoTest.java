package com.company.shippingcrudservice.dao;

import com.company.shippingcrudservice.model.Invoice;
import com.company.shippingcrudservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShippingDaoTest {

    @Autowired
    private ShippingDao dao;
    @Autowired
    private InvoiceItemDao itemDao;

    private static Invoice invoice;
    private static InvoiceItem invoiceItem;

    static {
        invoice = new Invoice(1, "30303", LocalDate.now(), BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_EVEN), BigDecimal.valueOf(0.07), BigDecimal.valueOf(12.99), new ArrayList<>());
    }

    @Before
    public void setUp() throws Exception {
        dao.deleteAll();
    }

    @Test
    public void test_saveOne() {
        invoice = dao.save(invoice);
        invoiceItem = new InvoiceItem(invoice.getInvoiceId(), "name", "desc", 130, BigDecimal.valueOf(12.99));
        invoiceItem = itemDao.save(invoiceItem);
    }

    @Test
    public void findOne() {
        invoice = dao.save(invoice);
        Optional<Invoice> test = dao.findById(invoice.getInvoiceId());
        assertTrue(test.isPresent());
        test.ifPresent(val-> assertEquals(invoice, test.get()));
    }

    @Test
    public void findAll() {
        dao.save(invoice);
        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void testFindAllByCustomerId() {
        dao.save(invoice);
        invoiceItem = new InvoiceItem(invoice.getInvoiceId(), "name", "desc", 130, BigDecimal.valueOf(12.99));
        itemDao.save(invoiceItem);

        assertEquals(1, dao.findAllByCustomerId(1).size());
    }

    @Test
    public void testUpdateInvoice() {

        invoice = dao.save(invoice);
        invoiceItem = new InvoiceItem(invoice.getInvoiceId(), "name", "desc", 130, BigDecimal.valueOf(12.99));
        itemDao.save(invoiceItem);
        invoice.setInvoiceItems(itemDao.findAllByInvoiceId(invoice.getInvoiceId()));

        dao.save(invoice);

        Optional<Invoice> getUpdated = dao.findById(invoice.getInvoiceId());

        assertTrue(getUpdated.isPresent());

    }

    @Test
    public void testDeleteInvoice() {
        invoice = dao.save(invoice);

        dao.deleteById(invoice.getInvoiceId());

        Optional<Invoice> getInvoice = dao.findById(invoice.getInvoiceId());

        assertFalse(getInvoice.isPresent());
    }

}