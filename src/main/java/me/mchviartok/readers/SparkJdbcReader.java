package me.mchviartok.readers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.Properties;

public final class SparkJdbcReader {
    private SparkJdbcReader(){}

    private static final Properties dbProperties = new Properties();
    private static final Properties connectionProperties = new Properties();

    static {
        try {
            dbProperties.load(Class.class.getResourceAsStream("/db.properties"));

            connectionProperties.setProperty("driver", dbProperties.getProperty("jdbc.driverClass"));
            connectionProperties.setProperty("user", dbProperties.getProperty("jdbc.user"));
            connectionProperties.setProperty("password", dbProperties.getProperty("jdbc.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Dataset<Row> read(final SparkSession spark, String table) {
        String url = dbProperties.getProperty("jdbc.url");
        return spark.read().jdbc(url, table, connectionProperties);
    }
}
