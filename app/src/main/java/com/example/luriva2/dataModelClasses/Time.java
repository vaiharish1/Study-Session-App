package com.example.luriva2.dataModelClasses;

public class Time implements Comparable<Time> {
    private final int hour, minute, second; // the hour and minute of this time

    // the constructor
    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // adding a certain amount of minutes
    public Time add(int newHours, int newMinutes, int newSeconds) {
        int s = second + newSeconds;
        int m = minute + newMinutes;
        int h = hour + newHours;

        m += (s / 60);
        s %= 60;

        h += (m / 60);
        m %= 60;

        h %= 24;
        return new Time(h,m,s);
    }

    // comparing this time to another time
    public int compareTo(Time other) {
        if (hour == other.getHour() && minute == other.getMinute()) return second - other.getSecond();
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

    // getting the second
    public int getSecond() {
        return second;
    }
}
