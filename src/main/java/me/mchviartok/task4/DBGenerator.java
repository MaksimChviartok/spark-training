package me.mchviartok.task4;

import me.mchviartok.AbstractTask;
import me.mchviartok.writers.SparkJdbcWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBGenerator extends AbstractTask {

    private static int PRODUCTS_COUNT = 1000;
    private static int ORDERS_COUNT = 10000;
    private static int ORDER_ITEMS_COUNT = ORDERS_COUNT * 10;

    private static int CATEGORIES_COUNT = 5;
    private static int CUSTOMERS_COUNT = 2_000;

    private static int MIN_QUANTITY = 1;
    private static int MAX_QUANTITY = 10;

    private final static long NOW = new Date().getTime();
    private final static int ONE_MINUTE = 1000 * 60;
    private final static int ONE_HOUR = ONE_MINUTE * 60;

    private static long random(long min, long max) {
        return min + (max - min) * RANDOM.nextLong();
    }

    private static int random(int min, int max) {
        return min + (max - min) * RANDOM.nextInt();
    }

    public static void main(String[] args) {
        final SparkSession spark = getSparkSession("Task #4. Database generator");

        final List<Product> products = new ArrayList<>(PRODUCTS_COUNT);
        for (int i = 0; i < PRODUCTS_COUNT; ++i) {
            final Long productId = (long) i;
            final Long categoryId = (long) RANDOM.nextInt(CATEGORIES_COUNT);
            products.add(new Product(productId, categoryId));
        }
        final Dataset<Product> productDataset = spark.createDataset(products, Encoders.bean(Product.class));

        final List<Order> orders = new ArrayList<>(ORDERS_COUNT);
        for (int i = 0; i < ORDERS_COUNT; ++i) {
            final Long orderId = (long) i;
            final Long customerId = random(0L, CUSTOMERS_COUNT);
            final Long completionTimestamp = NOW - random(0, ONE_HOUR);
            final Long creationTimestamp = completionTimestamp - random(ONE_MINUTE, ONE_HOUR * 24);
            orders.add(new Order(orderId, customerId, creationTimestamp, completionTimestamp));
        }
        final Dataset<Order> orderDataset = spark.createDataset(orders, Encoders.bean(Order.class));

        final List<OrderItem> orderItems = new ArrayList<>(ORDER_ITEMS_COUNT);
        for (int i = 0; i < ORDER_ITEMS_COUNT; ++i) {
            final Long itemId = (long) i;
            final Long orderId = (long) (i % ORDERS_COUNT);
            final Long productId = (long) RANDOM.nextInt(PRODUCTS_COUNT);
            final Integer quantity = MIN_QUANTITY + RANDOM.nextInt(MAX_QUANTITY - MIN_QUANTITY);
            final Long promotionId = 0L;
            final BigDecimal cost = BigDecimal.valueOf(1.0 + RANDOM.nextDouble() * 10);
            orderItems.add(new OrderItem(itemId, orderId, productId, quantity, promotionId, cost));
        }
        final Dataset<OrderItem> orderItemDataset = spark.createDataset(orderItems, Encoders.bean(OrderItem.class));

        SparkJdbcWriter.write(productDataset, PRODUCT_TABLE);
        SparkJdbcWriter.write(orderDataset, ORDER_TABLE);
        SparkJdbcWriter.write(orderItemDataset, ORDER_ITEM_TABLE);
    }
}
