package titov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import titov.prof.extentions.UIExtention;
import titov.prof.utils.Course;
import titov.prof.utils.ReduceCourses;
import org.junit.jupiter.api.Test;
import titov.prof.pages.CoursePage;
import java.util.List;

@ExtendWith(UIExtention.class)
public class EarlerCoursesTest {
  @Inject
  private CoursePage coursePage;
  @Inject
  ReduceCourses reduceCourses;

  /**
   * Открываем страницу каталога курсов https://otus.ru/catalog/courses
   * Найти курсы, которые стартуют раньше и позже всех. Если даты совпадают, то выбрать все такие курсы у которых дата совпадает.
   * Проверить, что на карточке самого раннего/позднего курсов отображается верное название курса и дата его начала
   * Для поиска таких курсов необходимо использовать stream api и reduce. Так же для проверки данных на странице карточки курса необходимо использовать jsoup.
   */
  @Test
  public void findEarliestAndLatestCourses() {
    coursePage
        .open()
        .getAllCourses();
    List<List<Course>> list = reduceCourses.getMinAndMaxDateCourses(coursePage.getListCourses());
    List<Course> minDateStartCourses = list.get(0);
    List<Course> maxDateStartCourses = list.get(1);
    for (Course minCourse : minDateStartCourses) {
      var course = coursePage.findCourseWithJSOUP(minCourse.getNameCourse());
      assertEquals(minCourse.getNameCourse(), course.getNameCourse(), "Наименования курсов не совпадают!");
      assertEquals(minCourse.getStartDate(), course.getStartDate(), "Даты начала курса не совпадают!");
    }
    for (Course maxCourse : maxDateStartCourses) {
      var course = coursePage.findCourseWithJSOUP(maxCourse.getNameCourse());
      assertEquals(maxCourse.getNameCourse(), course.getNameCourse(), "Наименования курсов не совпадают!");
      assertEquals(maxCourse.getStartDate(), course.getStartDate(), "Даты начала курса не совпадают!");
    }
  }
}
