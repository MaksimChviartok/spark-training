package me.mchviartok.readers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public final class SparkParquetReader {
    private SparkParquetReader() {
    }

    public static Dataset<Row> read(final SparkSession spark, String path) {
        return spark.read().parquet(path);
    }
}
