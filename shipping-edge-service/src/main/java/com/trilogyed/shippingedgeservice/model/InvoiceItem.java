package com.trilogyed.shippingedgeservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {
    private Integer invoiceItemId;
    private Integer invoiceId;
    private String itemName;
    private String itemDescription;
    private Integer weight;
    private BigDecimal shipCost;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer invoiceItemId, Integer invoiceId, String itemName, String itemDescription, Integer weight, BigDecimal shipCost) {
        this.invoiceItemId = invoiceItemId;
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
        this.shipCost = shipCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return Objects.equals(invoiceItemId, that.invoiceItemId) &&
                Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(itemDescription, that.itemDescription) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(shipCost, that.shipCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, invoiceId, itemName, itemDescription, weight, shipCost);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoiceId=" + invoiceId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", weight=" + weight +
                ", shipCost=" + shipCost +
                '}';
    }
}
