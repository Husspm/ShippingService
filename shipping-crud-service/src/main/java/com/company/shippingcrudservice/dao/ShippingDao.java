package com.company.shippingcrudservice.dao;

import com.company.shippingcrudservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingDao extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAllByCustomerId(Integer id);
}
