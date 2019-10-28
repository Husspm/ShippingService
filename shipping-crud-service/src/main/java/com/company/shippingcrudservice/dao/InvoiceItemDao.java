package com.company.shippingcrudservice.dao;

import com.company.shippingcrudservice.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemDao extends JpaRepository<InvoiceItem, Integer> {
    List<InvoiceItem> findAllByInvoiceId(Integer id);
}
