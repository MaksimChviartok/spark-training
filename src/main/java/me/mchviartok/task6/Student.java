package me.mchviartok.task6;

import org.apache.spark.sql.types.DataTypes;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
//                DataTypes.createStructField("id", DataTypes.StringType, false),
//            DataTypes.createStructField("firstName", DataTypes.StringType, false),
//            DataTypes.createStructField("lastName", DataTypes.StringType, false),
//            DataTypes.createStructField("score", DataTypes.DoubleType, false)
    private String id;
    private String firstName;
    private String lastName;
    private Double score;

    public Student() {
    }

    public Student(String id, String firstName, String lastName, Double score) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(score, student.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", score=" + score +
                '}';
    }
}
