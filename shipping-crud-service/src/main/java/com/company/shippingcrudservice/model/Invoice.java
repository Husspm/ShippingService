package com.company.shippingcrudservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoiceId;
    private Integer customerId;
    private String shipToZip;
    private LocalDate purchaseDate;
    private BigDecimal totalCost;
    private BigDecimal salesTax;
    private BigDecimal surcharge;
    @OneToMany(mappedBy = "invoiceId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<InvoiceItem> invoiceItems;

    public Invoice() {
    }

    public Invoice(Integer customerId, String shipToZip, LocalDate purchaseDate, BigDecimal totalCost, BigDecimal salesTax, BigDecimal surcharge, List<InvoiceItem> invoiceItems) {
        this.customerId = customerId;
        this.shipToZip = shipToZip;
        this.purchaseDate = purchaseDate;
        this.totalCost = totalCost;
        this.salesTax = salesTax;
        this.surcharge = surcharge;
        this.invoiceItems = invoiceItems;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getShipToZip() {
        return shipToZip;
    }

    public void setShipToZip(String shipToZip) {
        this.shipToZip = shipToZip;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge.setScale(2, RoundingMode.HALF_EVEN);
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(invoiceId, invoice.invoiceId) &&
                customerId.equals(invoice.customerId) &&
                shipToZip.equals(invoice.shipToZip) &&
                purchaseDate.equals(invoice.purchaseDate) &&
                totalCost.equals(invoice.totalCost) &&
                salesTax.equals(invoice.salesTax) &&
                surcharge.equals(invoice.surcharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, shipToZip, purchaseDate, totalCost, salesTax, surcharge);
    }
}
