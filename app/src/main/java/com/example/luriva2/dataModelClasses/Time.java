package com.example.luriva2.dataModelClasses;

public class Time implements Comparable<Time> {
    private final int hour, minute; // the hour and minute of this time

    // the constructor
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // adding a certain amount of minutes
    public Time add(int newHours, int newMinutes) {
        int h = hour + newHours;
        int m = minute + newMinutes;

        h += (m / 60);
        m %= 60;

        h %= 24;
        return new Time(h,m);
    }

    // comparing this time to another time
    public int compareTo(Time other) {
        if (hour == other.getHour()) return minute - other.getMinute();
        return hour - other.getHour();
    }

    // formatting the time
    public String toString() {
        String str = "";
        if (hour < 10) {
            str += "0";
        }
        str += Integer.toString(hour);
        str += ":";
        if (minute < 10) {
            str += "0";
        }
        str += Integer.toString(minute);
        return str;
    }

    // getting the hour
    public int getHour() {
        return hour;
    }

    // getting the minute
    public int getMinute() {
        return minute;
    }
}
