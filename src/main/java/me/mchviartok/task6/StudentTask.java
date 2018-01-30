package me.mchviartok.task6;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import me.mchviartok.AbstractTask;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.mllib.stat.test.ChiSqTestResult;
import org.apache.spark.mllib.stat.test.KolmogorovSmirnovTestResult;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.nio.file.Paths;

public class StudentTask extends AbstractTask {
    private static final int SAMPLE_COUNT = 1000;

    public static void main(String[] args) {
        final SparkSession spark = getSparkSession("Task #6. Student's score distribution test");

        Dataset<Student> rowDataset = spark.read()
                .parquet(Paths.get(STUDENTS_DIR, STUDENTS_PARQUET_FILE).toString())
                .as(Encoders.bean(Student.class));


        JavaDoubleRDD scoreDoubleRDD = rowDataset
                .javaRDD()
                .map(Student::getScore)
                .mapToDouble(s -> s);

        ChiSqTestResult uniformDistributionTestResult = Statistics.chiSqTest(
                Vectors.dense(Doubles.toArray(Longs.asList(scoreDoubleRDD.histogram(SAMPLE_COUNT)._2())))
        );

        KolmogorovSmirnovTestResult normalDistributionTestResult =
                Statistics.kolmogorovSmirnovTest(scoreDoubleRDD, "norm", 0.0, 1.0);

        System.out.println("Test to uniform distribution");
        System.out.println(uniformDistributionTestResult);
        System.out.println();
        System.out.println("Test to normal distribution");
        System.out.println(normalDistributionTestResult);
    }
}
