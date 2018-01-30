package me.mchviartok.task5;

import me.mchviartok.AbstractTask;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CallLogGenerator extends AbstractTask {

    private static final int UNIQUE_SUBSCRIBERS_COUNT = 1000;
    private static final int LOG_RECORDS_COUNT = 2_000_000;

    private static final List<String> SUBSCRIBERS = getSubscribers();
    private static final String REGIONS[] = {"BY", "RU", "UA", "KZ", "PL", "CH", "UK"};

    private final static long NOW = new Date().getTime();
    private final static int ONE_MINUTE = 1000 * 60;
    private final static int ONE_HOUR = ONE_MINUTE * 60;

    private static void clean() throws IOException {
        FileUtils.deleteDirectory(Paths.get(CALL_LOG_DIR, CALL_LOG_PARQUET_FILE).toFile());
        FileUtils.deleteDirectory(Paths.get(CALL_LOG_DIR, CALL_LOG_CSV_FILE).toFile());
    }

    private static List<String> getSubscribers() {
        final List<String> result = new ArrayList<>(UNIQUE_SUBSCRIBERS_COUNT);
        for (int i = 0; i < UNIQUE_SUBSCRIBERS_COUNT; ++i) {
            result.add(UUID.randomUUID().toString());
        }
        return result;
    }

    private static List<CallRecord> createCallLog(int count) {
        final List<CallRecord> result = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            int from_index = RANDOM.nextInt(SUBSCRIBERS.size());
            int to_index = RANDOM.nextInt(SUBSCRIBERS.size());
            if (from_index == to_index) {
                to_index = (to_index + 1) % SUBSCRIBERS.size();
            }
            String uuid = UUID.randomUUID().toString();
            String[] strings = uuid.split("-");

            final long timeStamp = NOW - RANDOM.nextInt(ONE_HOUR);
            final String from = SUBSCRIBERS.get(from_index);
            final String to = SUBSCRIBERS.get(to_index);
            final long duration = ONE_MINUTE + RANDOM.nextInt(ONE_HOUR * 3 - ONE_MINUTE);
            final String region = REGIONS[RANDOM.nextInt(REGIONS.length)];
            final String position = "position_" + strings[1];

            result.add(new CallRecord(timeStamp, from, to, duration, region, position));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        clean();

        final SparkSession spark = getSparkSession("Task #5. Call log generator");
        final JavaSparkContext javaSparkContext = new JavaSparkContext(spark.sparkContext());
        final SQLContext sqlContext = spark.sqlContext();

        final List<CallRecord> callLog = createCallLog(LOG_RECORDS_COUNT);
        final JavaRDD<CallRecord> callLogJavaRDD = javaSparkContext.parallelize(callLog);
        final Dataset<Row> callLogDataset = sqlContext.createDataFrame(callLogJavaRDD, CallRecord.class).toDF();

        callLogDataset.write().parquet(Paths.get(CALL_LOG_DIR, CALL_LOG_PARQUET_FILE).toString());
        callLogDataset.write().option("header", "true").csv(Paths.get(CALL_LOG_DIR, CALL_LOG_CSV_FILE).toString());
    }
}
