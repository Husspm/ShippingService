package com.company.shippingcrudservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoiceItemId;
    private Integer invoiceId;
    private String itemName;
    private String itemDescription;
    private Integer weight;
    private BigDecimal shipCost;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer invoiceId, String itemName, String itemDescription, Integer weight, BigDecimal shipCost) {
        this.invoiceId = invoiceId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.weight = weight;
        this.shipCost = shipCost;
    }

    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getShipCost() {
        return shipCost;
    }

    public void setShipCost(BigDecimal shipCost) {
        this.shipCost = shipCost.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return Objects.equals(invoiceItemId, that.invoiceItemId) &&
                invoiceId.equals(that.invoiceId) &&
                itemName.equals(that.itemName) &&
                itemDescription.equals(that.itemDescription) &&
                weight.equals(that.weight) &&
                shipCost.equals(that.shipCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, invoiceId, itemName, itemDescription, weight, shipCost);
    }
}
