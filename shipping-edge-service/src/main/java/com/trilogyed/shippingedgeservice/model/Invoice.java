package com.trilogyed.shippingedgeservice.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private Integer invoiceId;
    private Integer customerId;
    private String shiptoZip;
    private LocalDate purchaseDate;
    private BigDecimal totalCost;
    private BigDecimal salesTax;
    private BigDecimal surcharge;
    List<InvoiceItem> invoiceItems;

    public Invoice() {
    }

    public Invoice(Integer invoiceId, Integer customerId, String shiptoZip, LocalDate purchaseDate, BigDecimal totalCost, BigDecimal salesTax, BigDecimal surcharge, List<InvoiceItem> invoiceItems) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.shiptoZip = shiptoZip;
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

    public String getShiptoZip() {
        return shiptoZip;
    }

    public void setShiptoZip(String shiptoZip) {
        this.shiptoZip = shiptoZip;
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
        this.totalCost = totalCost;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
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
                Objects.equals(customerId, invoice.customerId) &&
                Objects.equals(shiptoZip, invoice.shiptoZip) &&
                Objects.equals(purchaseDate, invoice.purchaseDate) &&
                Objects.equals(totalCost, invoice.totalCost) &&
                Objects.equals(salesTax, invoice.salesTax) &&
                Objects.equals(surcharge, invoice.surcharge) &&
                Objects.equals(invoiceItems, invoice.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, shiptoZip, purchaseDate, totalCost, salesTax, surcharge, invoiceItems);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", shiptoZip='" + shiptoZip + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", totalCost=" + totalCost +
                ", salesTax=" + salesTax +
                ", surcharge=" + surcharge +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}
