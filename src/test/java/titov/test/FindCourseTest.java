package titov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import titov.prof.extentions.UIExtention;
import titov.prof.pages.CoursePage;

@ExtendWith(UIExtention.class)
public class FindCourseTest {
  @Inject
  private CoursePage coursePage;

  /**
   * @param courseName Открываем страницу каталога курсов https://otus.ru/catalog/courses
   *                   Найти курс по имени (имя курса должно передаваться как данные в тесте)
   *                   Кликнуть по плитке курса и проверить, что открыта страница верного курса
   *                   Для поиска курса по имени обязательно необходимо использовать stream api.
   */
  @ParameterizedTest
  @ValueSource(strings = {"Python Developer. Professional", "Golang Developer. Professional", "DevOps практики и инструменты", "Data Engineer"})
  public void findCourseByNameTest(String courseName) {
    assertEquals(courseName, coursePage
        .open()
        .findCourse(courseName)
        .searchCourseByName(courseName)
        .getHeaderCourse(), "Названия курсов не совпадают!");
  }
}
