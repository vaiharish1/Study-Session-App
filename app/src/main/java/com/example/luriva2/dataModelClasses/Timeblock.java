package com.example.luriva2.dataModelClasses;

public class Timeblock implements Comparable<Timeblock> {
    private Time startTime;
    private Time endTime;

    public Timeblock(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public boolean contains(Time otherTime) {
        if (startTime.equals(otherTime)) return true;
        if (startTime.compareTo(otherTime) <= 0 && endTime.compareTo(otherTime) > 0) return true;
        return false;
    }

    public boolean intersects(Timeblock other) {
        // if endpoint of other is between
        if (startTime.compareTo(other.getEndTime()) < 0 && endTime.compareTo(other.getEndTime()) > 0) return true;
        if (startTime.compareTo(other.getStartTime()) < 0 && endTime.compareTo(other.getStartTime()) > 0) return true;
        return false;
    }

    public int compareTo(Timeblock other) {
        return this.getStartTime().compareTo(other.getStartTime());
    }

    @Override
    public String toString() {
        return "Timeblock{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}