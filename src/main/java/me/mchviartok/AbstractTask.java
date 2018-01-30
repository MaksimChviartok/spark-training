package me.mchviartok;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public abstract class AbstractTask {
    protected static Random RANDOM = new Random();

    private static final Properties appProperties = new Properties();
    static {
        try {
            appProperties.load(Class.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static final String STUDENTS_PARQUET_FILE = appProperties.getProperty("students.parquet_file");
    protected static final String STUDENTS_CSV_FILE = appProperties.getProperty("students.csv_file");
    protected static final String STUDENTS_DIR = appProperties.getProperty("students.dir");

    protected static final String CALL_LOG_PARQUET_FILE = appProperties.getProperty("call_log.parquet_file");
    protected static final String CALL_LOG_CSV_FILE = appProperties.getProperty("call_log.csv_file");
    protected static final String CALL_LOG_DIR = appProperties.getProperty("call_log.dir");

    protected static final String PRODUCT_TABLE = appProperties.getProperty("product_table");
    protected static final String ORDER_ITEM_TABLE = appProperties.getProperty("order_item_table");
    protected static final String ORDER_TABLE = appProperties.getProperty("order_table");

    protected static SparkSession getSparkSession(String appName) {
        final SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[*]");
        sparkConf.setAppName(appName);
        return SparkSession.builder().config(sparkConf).getOrCreate();

    }
}
