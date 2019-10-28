package com.trilogyed.shippingedgeservice.viewmodel;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Order {
    @NotNull
    @Positive
    private Integer customerId;
    @NotEmpty
    @Size(min = 5, max = 5)
    private String shiptoZip;
    @NotEmpty
    @Valid
    private List<Item> items;

    public Order() {
    }

    public Order(Integer customerId, String shiptoZip, List<Item> items) {
        this.customerId = customerId;
        this.shiptoZip = shiptoZip;
        this.items = items;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customerId, order.customerId) &&
                Objects.equals(shiptoZip, order.shiptoZip) &&
                Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, shiptoZip, items);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId=" + customerId +
                ", shiptoZip='" + shiptoZip + '\'' +
                ", items=" + items +
                '}';
    }
}
