package me.mchviartok.task4;

import java.math.BigDecimal;
import java.util.Objects;

//Product(product_id, category_id)
public class ProductWithRevenue extends Product {
    private BigDecimal revenue;

    public ProductWithRevenue() {
        super();
    }

    public ProductWithRevenue(Long productId, Long categoryId, BigDecimal totalCost) {
        super(productId, categoryId);
        this.revenue = totalCost;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductWithRevenue that = (ProductWithRevenue) o;
        return Objects.equals(revenue, that.revenue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), revenue);
    }
}
