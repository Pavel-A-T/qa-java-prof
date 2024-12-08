package com.pages;

import java.time.LocalDate;

import static com.utils.DateComparison.parseDate;

public class Course {
    String nameCourse;
    String startDate;
    LocalDate date;

    @Override
    public String toString() {
        return "Course{" +
                "nameCourse='" + nameCourse + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }
    public String getNameCourse() {
        return nameCourse;
    }
    public String getStartDate() {
        return startDate;
    }

    public Course(String nameCourse, String startDate) {
           this.nameCourse = nameCourse;
           this.startDate = startDate;
           this.date = parseDate(startDate);
    }
}
