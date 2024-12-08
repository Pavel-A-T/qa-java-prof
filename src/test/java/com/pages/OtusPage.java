package com.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

import static com.utils.Constants.*;

@Component
public class OtusPage {
    private ApplicationContext context;
    private WebDriver driver;
    private boolean accept = false;
    int wait = 800;
    By link = By.xpath("//a/h6");
    By pathButtonSeeMore = By.xpath("//button[contains(text(),\"Показать еще\")]");
    By pathHeader = By.cssSelector("h1");
    By pathCookie = By.xpath("//button/div[contains(text(),'OK')]");
    By linkWithClass = By.xpath("//a[contains(@class,\"sc-zzdkm7-0\")]");
    By courseDate = By.xpath("./div/div/div[contains(@class,\"jEGzDf\") and not(*)]");
    By courseName = By.xpath("./h6/div");
    By spanStudy = By.xpath("//span[contains(@title,\"Обучение\")]");
    By xPathCategories = By.xpath("//a[contains(@href,\"https://otus.ru/categories\")]");
    By checkBoxCourses = By.xpath("./ancestor::div[contains(@class,\"eQrMuA\")]");

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private static void scrollToElementCenter(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Скролл так, чтобы элемент оказался по центру страницы
        js.executeScript(
                "const viewportHeight = window.innerHeight;" +
                        "const elementTop = arguments[0].getBoundingClientRect().top;" +
                        "window.scrollBy(0, elementTop - (viewportHeight / 2));", element);
    }

    private By getTitleCourse(String course) {
        return By.xpath("//a/h6/div[contains(text(),\"" + course + "\")]");
    }

    private By getLabel(String label) {
        return By.xpath("//label[contains(text(),\"" + label + "\")]");
    }

    // Добавление рамки к элементу
    private void highlightElement(WebElement element, String styleBox) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='" + styleBox + "'", element);
    }

    private boolean getMore() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            wait.until(ExpectedConditions.elementToBeClickable(pathButtonSeeMore));
        } catch (Exception e) {
        }
        List<WebElement> list = driver.findElements(pathButtonSeeMore);
        if (list.size() > 0) {
            WebElement element = list.get(0);
            scrollToElementCenter(driver, element);
            highlightElement(element, STYLE_BOX_RED);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException inter) {
            }
            element.click();
            return true;
        }
        return false;
    }

    public List<Course> getAllCourses() {
        Map<String, String> courses = new HashMap<>();
        List<Course> result = new ArrayList<>();
        open(OTUS_COURSES_URL);
        do {
            List<WebElement> elements = driver.findElements(linkWithClass);
            elements.forEach(o -> {
                try {
                    String value = o.findElement(courseDate).getText();
                    String key = o.findElement(courseName).getText();
                    courses.put(key, value);
                } catch (Exception e) {
                }
            });
        } while (getMore());
        for (String key : courses.keySet()) {
            result.add(context.getBean(Course.class, key, courses.get(key)));   //new Course(key, courses.get(key)));
        }
        return result;
    }

    public Course findCourseWithJSOUP(String courseName) {
        String className = "sc-zzdkm7-0";
        String h6CSSQuery = "h6 > div";
        String cssQuery = "h6 + div > div > div";
        open(OTUS_COURSES_URL);
        try {
            do {
                String pageSource = driver.getPageSource();
                Document doc = Jsoup.parse(pageSource);
                Elements links = doc.getElementsByClass(className);
                for (Element element : links) {
                    String jsoupCourse = element.select(h6CSSQuery).text();
                    if (courseName.equals(jsoupCourse)) {
                        String date = element.select(cssQuery).text();
                        return new Course(jsoupCourse, date);
                    }
                }
            } while (getMore());
        } catch (Exception e) {
            System.out.println("Ошибка в JSOUP: " + e.getMessage());
        }
        return null;
    }

    public void acceptCookies() {
        WebElement cookie;
        while (!accept) {
            try {
                // Ожидание появления элемента с текстом "OK"
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                cookie = wait.until(ExpectedConditions.presenceOfElementLocated(pathCookie));
                cookie.click();
                accept = true;
            } catch (Exception e) {
                System.out.println("Не приняты cookies по причине: " + e.getMessage());
            }
        }
    }

    public void findCourse(String courseName) {
        WebElement foundCourse = null;
        By div = By.xpath("./div");
        List<String> courses = new ArrayList<>();
        open(OTUS_COURSES_URL);
        while (foundCourse == null) {
            List<WebElement> links = driver.findElements(link);
            for (WebElement e : links) {
                try {
                    WebElement elementFromLinks = (e.findElement(div));
                    if (elementFromLinks != null) {
                        courses.add(elementFromLinks.getText());
                    }
                } catch (Exception exp) {
                }
            }
            Optional<String> optional = courses.stream().filter(o -> o.equals(courseName)).findFirst();
            if (optional.isPresent()) {
                foundCourse = driver.findElement(getTitleCourse(optional.get()));
            }
            if (foundCourse != null) {
                scrollToElementCenter(driver, foundCourse);
                highlightElement(foundCourse, STYLE_BOX_YELLOW);
                Actions actions = new Actions(driver);
                // Выполняем двойной клик по элементу
                actions.doubleClick(foundCourse).perform();
                try {
                    Thread.sleep(wait);
                } finally {
                    return;
                }
            } else {
                if (getMore()) {
                    courses = new ArrayList<>();
                    continue;
                }
                break;
            }
        }
    }

    public String isCheckedFinedElement() throws InterruptedException {
        String value = "value";
        open(OTUS_URL);
        WebElement study = this.driver.findElement(spanStudy);
        scrollToElementCenter(driver, study);
        List<WebElement> categories = this.driver.findElements(xPathCategories);
        int max = categories.size();
        int random = (int) (Math.random() * max);
        WebElement element = categories.get(random);
        //Подсвечиваем элемент рамкой
        highlightElement(study, STYLE_BOX);
        //Задержка для наблюдения
        Thread.sleep(wait);
        study.click();
        highlightElement(element, STYLE_BOX);
        Thread.sleep(wait);
        String text = element.getText().replaceAll("\\s*\\(\\d+\\)", "").trim();
        element.click();
        WebElement webElement = this.driver.findElement(getLabel(text));
        WebElement result = webElement.findElement(checkBoxCourses);
        return result.getDomAttribute(value);
    }

    public WebElement getHeader() {
        return driver.findElement(pathHeader);
    }

    public void open(String url) {
        driver.get(url);
    }

    public OtusPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

}
