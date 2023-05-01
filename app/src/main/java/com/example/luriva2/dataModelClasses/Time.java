package com.example.luriva2.dataModelClasses;

public class Time implements Comparable<Time> {
    private int hour, minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time add(int newHours, int newMinutes) {
        int h = hour + newHours;
        int m = minute + newMinutes;

        h += (m / 60);
        m %= 60;

        h %= 24;
        return new Time(h,m);
    }

    public Time subtract(int newHours, int newMinutes) {
        int h = hour - newHours;
        int m = minute - newMinutes;

        while (m < 0) {
            h--;
            m += 60;
        }

        h += (m/60);
        m %= 60;

        while (h < 0) {
            h += 24;
        }
        return new Time(h,m);
    }

    public int compareTo(Time other) {
        if (hour == other.getHour()) return minute - other.getMinute();
        return hour - other.getHour();
    }

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

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
