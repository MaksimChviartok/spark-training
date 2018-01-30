package me.mchviartok.task4;

import java.io.Serializable;
import java.util.Objects;

//Order(order_id, customer_id, creation_tmst, completion_tmst)
public class Order implements Serializable {
    private Long orderId;
    private Long customerId;
    private Long creationTimestamp;
    private Long completionTimestamp;

    public Order() {
    }

    public Order(Long orderId, Long customerId, Long creationTimestamp, Long completionTimestamp) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.creationTimestamp = creationTimestamp;
        this.completionTimestamp = completionTimestamp;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Long getCompletionTimestamp() {
        return completionTimestamp;
    }

    public void setCompletionTimestamp(Long completionTimestamp) {
        this.completionTimestamp = completionTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(customerId, order.customerId) &&
                Objects.equals(creationTimestamp, order.creationTimestamp) &&
                Objects.equals(completionTimestamp, order.completionTimestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, customerId, creationTimestamp, completionTimestamp);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", creationTimestamp=" + creationTimestamp +
                ", completionTimestamp=" + completionTimestamp +
                '}';
    }
}
