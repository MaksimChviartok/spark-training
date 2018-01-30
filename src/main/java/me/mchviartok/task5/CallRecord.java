package me.mchviartok.task5;

import java.io.Serializable;
import java.util.Objects;

public class CallRecord implements Serializable {
    private long timeStamp;
    private String from;
    private String to;
    private long duration;
    private String region;
    private String position;

    public CallRecord() {
    }

    public CallRecord(long timeStamp, String from, String to, long duration, String region, String position) {
        this.timeStamp = timeStamp;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.region = region;
        this.position = position;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallRecord that = (CallRecord) o;
        return timeStamp == that.timeStamp &&
                duration == that.duration &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(region, that.region) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timeStamp, from, to, duration, region, position);
    }

    @Override
    public String toString() {
        return "CallRecord{" +
                "timeStamp=" + timeStamp +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", duration=" + duration +
                ", region='" + region + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
