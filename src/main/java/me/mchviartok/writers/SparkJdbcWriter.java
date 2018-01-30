package me.mchviartok.writers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.Properties;

final public class SparkJdbcWriter {
    private SparkJdbcWriter() {
    }

    private static final Properties dbProperties = new Properties();
    private static final Properties connectionProperties = new Properties();

    static {
        try {
            dbProperties.load(Class.class.getResourceAsStream("/db.properties"));

            connectionProperties.setProperty("driver", dbProperties.getProperty("jdbc.driverClass"));
            connectionProperties.setProperty("user", dbProperties.getProperty("jdbc.user"));
            connectionProperties.setProperty("password", dbProperties.getProperty("jdbc.password"));
            connectionProperties.setProperty("url", dbProperties.getProperty("jdbc.url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(final Dataset dataset, String table) {
        dataset.write().format("jdbc")
                .option("url", (String) connectionProperties.get("url"))
                .option("driver", (String) connectionProperties.get("driver"))
                .option("user", (String) connectionProperties.get("user"))
                .option("password", (String) connectionProperties.get("password"))
                .option("dbtable", table)
                .option("isolationLevel", "SERIALIZABLE")
                .mode(SaveMode.Overwrite).save();
    }
}
