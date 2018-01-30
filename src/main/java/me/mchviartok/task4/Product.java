package me.mchviartok.task4;

import java.io.Serializable;
import java.util.Objects;

//Product(product_id, category_id)
public class Product implements Serializable {
    private Long productId;
    private Long categoryId;

    public Product() {
    }

    public Product(Long productId, Long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
                Objects.equals(categoryId, product.categoryId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, categoryId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                '}';
    }
}
