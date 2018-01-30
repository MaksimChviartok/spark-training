package me.mchviartok.task4;

import me.mchviartok.AbstractTask;
import me.mchviartok.readers.SparkJdbcReader;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import scala.Tuple2;

import java.math.BigDecimal;

public class ProductTask extends AbstractTask {

    private static final int TOP = 3;
    private static final String REVENUE = "revenue";
    private static final String PRODUCT_ID = "productId";
    private static final String CATEGORY_ID = "categoryId";
    private static final String SUM_REVENUE = "sum(revenue)";
    private static final String RANK = "rank";

    public static void main(String[] args) {
        final SparkSession spark = getSparkSession("Task #4. ProductTask");

        final Dataset<Product> productDataset = SparkJdbcReader
                .read(spark, PRODUCT_TABLE)
                .as(Encoders.bean(Product.class))
                .cache();
        final Dataset<OrderItem> orderItemDataset = SparkJdbcReader
                .read(spark, ORDER_ITEM_TABLE)
                .as(Encoders.bean(OrderItem.class))
                .cache();

        final Dataset<ProductWithRevenue> productWithRevenueDataset = orderItemDataset
                .joinWith(productDataset,
                        orderItemDataset.col(PRODUCT_ID).equalTo(productDataset.col(PRODUCT_ID)),
                        "inner"
                ).map((MapFunction<Tuple2<OrderItem, Product>, ProductWithRevenue>) value -> {
                    final OrderItem orderItem = value._1();
                    final Product product = value._2();
                    final BigDecimal totalCost = BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getCost());
                    return new ProductWithRevenue(product.getProductId(), product.getCategoryId(), totalCost);
                }, Encoders.bean(ProductWithRevenue.class))
                .groupBy(PRODUCT_ID, CATEGORY_ID)
                .sum(REVENUE)
                .map((MapFunction<Row, ProductWithRevenue>) value ->
                                new ProductWithRevenue(
                                        value.getAs(PRODUCT_ID),
                                        value.getAs(CATEGORY_ID),
                                        value.getAs(SUM_REVENUE)
                                ),
                        Encoders.bean(ProductWithRevenue.class)
                );

        WindowSpec categoryIdWindowSpec = Window.partitionBy(CATEGORY_ID).orderBy(productWithRevenueDataset.col(REVENUE).desc());

        productWithRevenueDataset
                .withColumn(RANK, org.apache.spark.sql.functions.rank().over(categoryIdWindowSpec))
                .filter((FilterFunction<Row>) value -> TOP >= (int) value.getAs(RANK))
                .show();
    }
}
