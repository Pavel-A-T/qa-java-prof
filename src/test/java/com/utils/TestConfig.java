package com.utils;

import com.pages.Course;
import com.pages.OtusPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfig {
    @Bean
    @Qualifier("chromeDriver")
    public WebDriver chromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions"); // Отключение расширений
        options.addArguments("--window-size=1920x1080");
        options.addArguments("--disable-dev-shm-usage"); // Для устранения ошибок shared memor
        options.addArguments("--disable-software-rasterizer"); // Отключение аппаратного рендеринга
        options.addArguments("--disable-accelerated-2d-canvas");
        return new ChromeDriver(options);
    }

    @Bean
    @Scope("prototype")
    public Course course(String param1, String param2) {
        return new Course(param1, param2);
    }

    @Bean
    public OtusPage otusPage( WebDriver webDriver) {
        return new OtusPage(webDriver);
    }

    @Bean
    public DateComparison dateComparison() {
        return new DateComparison();
    }

    @Bean
    ReduceCourses reduceCourses() {
        return new ReduceCourses();
    }
}
