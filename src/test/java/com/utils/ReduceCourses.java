package com.utils;

import com.pages.Course;

import java.util.ArrayList;
import java.util.List;

public class ReduceCourses {
    public List<List<Course>> getMinAndMaxDateCourses(List<Course> courses) {
        List<Course> minDateCourses = courses.stream()
                .filter(course -> course.getDate() != null)
                .reduce(new ArrayList<>(),
                        (acc, course) -> {
                            if (acc.isEmpty()) {
                                acc.add(course);
                            } else {
                                int comparison = course.getDate().compareTo(acc.get(0).getDate());
                                if (comparison < 0) {
                                    acc.clear();
                                    acc.add(course);
                                } else if (comparison == 0) {
                                    acc.add(course);
                                }
                            }
                            return acc;
                        },
                        (acc1, acc2) -> {
                            if (acc1.isEmpty()) return acc2;
                            if (acc2.isEmpty()) return acc1;
                            int comparison = acc1.get(0).getDate().compareTo(acc2.get(0).getDate());
                            if (comparison < 0) {
                                return acc1;
                            } else if (comparison > 0) {
                                return acc2;
                            } else {
                                acc1.addAll(acc2);
                                return acc1;
                            }
                        });

        List<Course> maxDateCourses = courses.stream()
                .filter(course -> course.getDate() != null)
                .reduce(new ArrayList<>(),
                        (acc, course) -> {
                            if (acc.isEmpty()) {
                                acc.add(course);
                            } else {
                                int comparison = course.getDate().compareTo(acc.get(0).getDate());
                                if (comparison > 0) {
                                    acc.clear();
                                    acc.add(course);
                                } else if (comparison == 0) {
                                    acc.add(course);
                                }
                            }
                            return acc;
                        },
                        (acc1, acc2) -> {
                            if (acc1.isEmpty()) return acc2;
                            if (acc2.isEmpty()) return acc1;
                            int comparison = acc1.get(0).getDate().compareTo(acc2.get(0).getDate());
                            if (comparison > 0) {
                                return acc1;
                            } else if (comparison < 0) {
                                return acc2;
                            } else {
                                acc1.addAll(acc2);
                                return acc1;
                            }
                        });

        return List.of(minDateCourses, maxDateCourses);
    }
}

