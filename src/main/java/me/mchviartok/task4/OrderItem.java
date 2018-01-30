package me.mchviartok.task4;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

//OrderItem(item_id, order_id, product_id, qty, promotion_id, cost)
public class OrderItem implements Serializable {
    private Long itemId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Long promotionId;
    private BigDecimal cost;

    public OrderItem() {
    }

    public OrderItem(Long itemId, Long orderId, Long productId, Integer quantity, Long promotionId, BigDecimal cost) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.promotionId = promotionId;
        this.cost = cost;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(itemId, orderItem.itemId) &&
                Objects.equals(orderId, orderItem.orderId) &&
                Objects.equals(productId, orderItem.productId) &&
                Objects.equals(quantity, orderItem.quantity) &&
                Objects.equals(promotionId, orderItem.promotionId) &&
                Objects.equals(cost, orderItem.cost);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemId, orderId, productId, quantity, promotionId, cost);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", promotionId=" + promotionId +
                ", cost=" + cost +
                '}';
    }
}
