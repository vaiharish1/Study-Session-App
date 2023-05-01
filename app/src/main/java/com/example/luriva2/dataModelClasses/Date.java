package com.example.luriva2.dataModelClasses;

import java.util.Objects;

public class Date implements Comparable<Date> {
    public static int[] daysOfMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
    private int month, day, year;

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return month == date.month && day == date.day && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, day, year);
    }

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

    public Date subtractDays(int days) {
        int d = day + days;
        int m = month;
        int y = year;
        while (d < 0) {
            d += daysOfMonths[m-1];
            m--;

            if (m == 0) {
                m = 12;
                y--;
            }
        }
        return new Date(m, d, y);
    }

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


    public int compareTo(Date other) {
        if (month == other.getMonth()) {
            return day - other.getDay();
        }
        return month - other.getMonth();
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
