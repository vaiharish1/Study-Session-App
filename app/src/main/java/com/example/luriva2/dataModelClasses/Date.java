package com.example.luriva2.dataModelClasses;

import java.util.Objects;

public class Date implements Comparable<Date> {
    public static int[] daysOfMonths = {31,28,31,30,31,30,31,31,30,31,30,31}; // number of days in each of the months
    private final int month, day, year;

    // constructing the date
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // if one date is equal to another
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return month == date.month && day == date.day && year == date.year;
    }

    // hash code of date using Objects method
    @Override
    public int hashCode() {
        return Objects.hash(month, day, year);
    }

    // adding days to the current date
    public Date addDays(int days) {
        int d = day + days;
        int m = month;
        int y = year;
        while (d > daysOfMonths[m-1]) {
            d -= daysOfMonths[m-1];
            m++;

            if (m == 13) {
                m = 1;
                y++;
            }
        }
        return new Date(m, d, y);
    }

    // if the date is valid (correct day of the month)
    public static boolean correctDate(Date d) {
        int dMonth = d.getMonth();
        int dDay = d.getDay();
        if (dMonth > 12 || dMonth < 0) return false;
        if (dDay > daysOfMonths[dMonth-1] || dDay < 0) return false;
        return true;
    }

    // formatting the date
    public String toString() {
        String str = "";
        if (month < 10) {
            str += "0";
        }
        str += Integer.toString(month);
        str += "/";
        if (day < 10) {
            str += "0";
        }
        str += Integer.toString(day);
        str += "/";
        str += Integer.toString(year);
        return str;
    }


    // comparing this date to other dates
    public int compareTo(Date other) {
        if (year == other.getYear() && month == other.getMonth()) return day - other.getDay();
        if (year == other.getYear()) return month - other.getMonth();
        return year - other.getYear();
    }

    // getting the year
    public int getYear() { return year; }

    // getting the month
    public int getMonth() {
        return month;
    }

    // getting the day
    public int getDay() {
        return day;
    }
}
