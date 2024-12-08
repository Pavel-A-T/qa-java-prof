package com.tests;

import com.pages.Course;
import com.pages.OtusPage;
import com.utils.ReduceCourses;
import com.utils.TestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestConfig.class)
public class OtusPageTest {
    @Autowired
    private static WebDriver driver;
    @Autowired
    private OtusPage page;
    @Autowired
    ReduceCourses reduceCourses;

    @ParameterizedTest
    @ValueSource(strings = {"Python Developer. Professional", "Golang Developer. Professional", "DevOps практики и инструменты", "Data Engineer"})
    public void findCourseByName(String courseName) {
        page.findCourse(courseName);
        assertEquals(courseName, page.getHeader().getText(), "Названия курсов не совпадают!");
    }

    @Test
    public void findEarliestAndLatestCourses() {
        List<List<Course>> list = reduceCourses.getMinAndMaxDateCourses(page.getAllCourses());
        List<Course> minDateStartCourses = list.get(0);
        List<Course> maxDateStartCourses = list.get(1);
        for (Course minCourse : minDateStartCourses) {
            var course = page.findCourseWithJSOUP(minCourse.getNameCourse());
            assertEquals(minCourse.getNameCourse(), course.getNameCourse(), "Наименования курсов не совпадают!");
            assertEquals(minCourse.getStartDate(), course.getStartDate(), "Даты начала курса не совпадают!");
        }
        for (Course maxCourse : maxDateStartCourses) {
            var course = page.findCourseWithJSOUP(maxCourse.getNameCourse());
            assertEquals(maxCourse.getNameCourse(), course.getNameCourse(), "Наименования курсов не совпадают!");
            assertEquals(maxCourse.getStartDate(), course.getStartDate(), "Даты начала курса не совпадают!");
        }

    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    public void checkCourseCatalog(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            assertEquals("true", page.isCheckedFinedElement(), "Элемент не был выбран!");
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("WebDriver закрыт.");
        }
    }
}
