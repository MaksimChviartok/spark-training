package me.mchviartok.task6;

import me.mchviartok.AbstractTask;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentsGenerator extends AbstractTask {
    private static final int STUDENTS_COUNT = 2_00_000;

    private static double random() {
        final int COUNT = 50;
        double result = 0;
        for (int i = 0; i < COUNT; ++i) {
            result += RANDOM.nextDouble();
        }
        return result / COUNT;
    }

    private static double random(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    private static void clean() throws IOException {
        FileUtils.deleteDirectory(Paths.get(STUDENTS_DIR, STUDENTS_PARQUET_FILE).toFile());
        FileUtils.deleteDirectory(Paths.get(STUDENTS_DIR, STUDENTS_CSV_FILE).toFile());
    }

    private static List<Student> createStudentList(int count) {
        final List<Student> result = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            String id = UUID.randomUUID().toString();
            String[] strings = id.split("-");
            result.add(new Student(
                    id,
                    "firstName_" + strings[0],
                    "lastName_" + strings[4],
//                    1.0
//                    random(-1.0, 1.0)
//                    RANDOM.nextDouble()
                    RANDOM.nextGaussian()
//                    random()
            ));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        clean();

        final SparkSession spark = getSparkSession("Task #6. Student's generator");

        final List<Student> list = createStudentList(STUDENTS_COUNT);
        final JavaSparkContext javaSparkContext = new JavaSparkContext(spark.sparkContext());
        final JavaRDD<Student> rowJavaRDD = javaSparkContext
                .parallelize(list).map(row -> row);


        final SQLContext sqlContext = spark.sqlContext();
        final Dataset<Row> rowDataset = sqlContext.createDataFrame(rowJavaRDD, Student.class).toDF();
        rowDataset
                .write()
//                .mode("append")
                .parquet(Paths.get(STUDENTS_DIR, STUDENTS_PARQUET_FILE).toString());
        rowDataset
                .write()
//                .mode("append")
                .csv(Paths.get(STUDENTS_DIR, STUDENTS_CSV_FILE).toString());
    }
}
