package com.example.luriva2.dataModelClasses;

public class Timeblock {
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

    public boolean intersects(Timeblock other) {
        // if endpoint of other is between
        if (startTime.compareTo(other.getEndTime()) < 0 && endTime.compareTo(other.getEndTime()) > 0) return true;
        if (startTime.compareTo(other.getStartTime()) < 0 && endTime.compareTo(other.getStartTime()) > 0) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Timeblock{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
