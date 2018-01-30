package me.mchviartok.task5;

import me.mchviartok.AbstractTask;
import me.mchviartok.SerializableComparator;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.nio.file.Paths;
import java.util.List;

public class CallLogTask extends AbstractTask {
    private static final int TOP = 5;

    private static Dataset<Row> getParquet(final SparkSession spark) {
        return spark.read()
                .parquet(Paths.get(CALL_LOG_DIR, CALL_LOG_PARQUET_FILE).toString());
    }

    private static Dataset<Row> getCsv(final SparkSession spark) {
        return spark.read()
                .option("header", "true")
                .schema(Encoders.bean(CallRecord.class).schema())
                .csv(Paths.get(CALL_LOG_DIR, CALL_LOG_CSV_FILE).toString());
    }

    private static List<Tuple2<String, Long>> top2(final Dataset<Row> rowDataset) {
        return rowDataset
                .as(Encoders.bean(CallRecord.class))
                .javaRDD()
                .mapToPair(
                        (PairFunction<CallRecord, String, Long>) record ->
                                new Tuple2<>(record.getFrom(), record.getDuration())
                )
                .reduceByKey((i1, i2) -> i1 + i2)
                .top(TOP, SerializableComparator.serialize(
                        (SerializableComparator<Tuple2<String, Long>>) (left, right) -> Long.compare(
                                left._2(),
                                right._2()
                        )
                ));
    }

    public static void main(String[] args) {

        final SparkSession spark = getSparkSession("Task #5. Call log reading performance");
        long count = spark.read()
                .parquet(Paths.get(CALL_LOG_DIR, CALL_LOG_PARQUET_FILE).toString())
                .count();
        spark.read()
                .parquet(Paths.get(CALL_LOG_DIR, CALL_LOG_PARQUET_FILE).toString());

        long parquetTestStartTime = System.currentTimeMillis();
        final List<Tuple2<String, Long>> topParquet = top2(getParquet(spark));
        long parquetTestEndTime = System.currentTimeMillis();

        long csvTestStartTime = System.currentTimeMillis();
        final List<Tuple2<String, Long>> topCsv = top2(getCsv(spark));
        long csvTestEndTime = System.currentTimeMillis();

        System.out.println(String.format(
                "Parquet 'Top %d' for %d records. Duration: %d ms",
                TOP,
                count,
                (parquetTestEndTime - parquetTestStartTime)
        ));
        topParquet.forEach(tuple -> System.out.println(String.format("Sub: %s, duration: %d", tuple._1(), tuple._2())));
        System.out.println();

        System.out.println(String.format(
                "CSV 'Top %d' for %d records. Duration: %d ms",
                TOP,
                count,
                (csvTestEndTime - csvTestStartTime)
        ));
        topCsv.forEach(tuple -> System.out.println(String.format("Sub: %s, duration: %d", tuple._1(), tuple._2())));
        System.out.println();
    }
}
