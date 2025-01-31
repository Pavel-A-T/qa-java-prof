package titov.prof.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReduceCourses {
  public List<List<Course>> getMinAndMaxDateCourses(List<Course> courses) {
    List<Course> minDateCourses = courses.stream()
        .filter(course -> course.getLocalDate() != null)
        .reduce(new ArrayList<>(),
            (acc, course) -> {
              if (acc.isEmpty()) {
                acc.add(course);
              } else {
                LocalDate accDate = acc.get(0).getLocalDate();
                LocalDate courseDate = course.getLocalDate();
                if (courseDate.isBefore(accDate)) {
                  acc.clear();
                  acc.add(course);
                } else if (courseDate.isEqual(accDate)) {
                  acc.add(course);
                }
              }
              return acc;
            },
            (acc1, acc2) -> {
              if (acc1.isEmpty()) return acc2;
              if (acc2.isEmpty()) return acc1;
              LocalDate acc1Date = acc1.get(0).getLocalDate();
              LocalDate acc2Date = acc2.get(0).getLocalDate();
              if (acc1Date.isBefore(acc2Date)) {
                return acc1;
              } else if (acc1Date.isAfter(acc2Date)) {
                return acc2;
              } else {
                acc1.addAll(acc2);
                return acc1;
              }
            });
    List<Course> maxDateCourses = courses.stream()
        .filter(course -> course.getLocalDate() != null)
        .reduce(new ArrayList<>(),
            (acc, course) -> {
              if (acc.isEmpty()) {
                acc.add(course);
              } else {
                LocalDate accDate = acc.get(0).getLocalDate();
                LocalDate courseDate = course.getLocalDate();
                if (courseDate.isAfter(accDate)) {
                  acc.clear();
                  acc.add(course);
                } else if (courseDate.isEqual(accDate)) {
                  acc.add(course);
                }
              }
              return acc;
            },
            (acc1, acc2) -> {
              if (acc1.isEmpty()) return acc2;
              if (acc2.isEmpty()) return acc1;
              LocalDate acc1Date = acc1.get(0).getLocalDate();
              LocalDate acc2Date = acc2.get(0).getLocalDate();
              if (acc1Date.isAfter(acc2Date)) {
                return acc1;
              } else if (acc1Date.isBefore(acc2Date)) {
                return acc2;
              } else {
                acc1.addAll(acc2);
                return acc1;
              }
          });
    return List.of(minDateCourses, maxDateCourses);
  }
}
