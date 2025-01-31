package titov.prof.utils;

import static titov.prof.utils.DateComparison.parseDate;

import java.time.LocalDate;

public class Course {
  String nameCourse;
  String startDate;
  LocalDate date;

  public LocalDate getLocalDate() {
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
