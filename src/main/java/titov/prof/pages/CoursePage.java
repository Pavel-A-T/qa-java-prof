package titov.prof.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import titov.prof.commons.annotaitions.Path;
import titov.prof.utils.Course;
import java.util.*;

@Path("/catalog/courses")
public class CoursePage extends AbsBasePage<CoursePage> {
  private List<Course> allCourses = new ArrayList<>();
  @FindBy(xpath = "//input[@type=\"search\"]")
  private WebElement search;
  private By linkWithClass = By.className("sc-zzdkm7-0");
  private By pathButtonSeeMore = By.xpath("//button[contains(text(),\"Показать еще\")]");
  private By courseDate = By.xpath("./div/div/div[contains(@class,\"jEGzDf\") and not(*)]");
  private By courseNamePath = By.xpath("./h6/div");

  public CoursePage(WebDriver webDriver) {
    super(webDriver);
  }

  public List<Course> getListCourses() {
    return this.allCourses;
  }

  public CoursePage findCourse(String courseName) {
    search.sendKeys(courseName + Keys.ENTER);
    return this;
  }

  public LessonPage searchCourseByName(String courseName) {
    String currentCourseName = "//a/h6/div[contains(text(),\"" + "%s" + "\")]";
    waiter.waitForElementVisibleByLocator(By.xpath(String.format(currentCourseName, courseName)));
    List<WebElement> courses = driver.findElements(linkWithClass);
    Optional<WebElement> optional = courses.stream()
        .filter(Objects::nonNull)
        .map(o->o.findElement(courseNamePath))
        .filter(o -> {
          try {
            return courseName.trim().equalsIgnoreCase(o.getText().trim());
          } catch (Exception e) {
            return false;
          }
        })
        .findFirst();
    if (optional.isPresent()) {
      WebElement element = optional.get();
      try {
        element.click();
      }
      catch (Exception e) {
        goBlockCentre(element);
        element.click();
      }
      return page(LessonPage.class);
    }
    throw new RuntimeException(courseName + " не найден!");
  }

  public CoursePage getAllCourses() {
    int i = 0;
    Map<String, String> courses = new HashMap<>();
    List<WebElement> moreList;
    do {
      i++;
      List<WebElement> elements = driver.findElements(linkWithClass);
      elements
          .stream()
          .filter(Objects::nonNull)
          .forEach(o -> {
            try {
              String value = o.findElement(courseDate).getText();
              String key = o.findElement(courseNamePath).getText();
              courses.put(key, value);
            } catch (Exception exp) {
              Throwable ignored = exp;
            }
          });
      waiter.waitForElementVisibleByLocator(pathButtonSeeMore);
      moreList = driver.findElements(pathButtonSeeMore);
      if (moreList.size() == 1) {
        goBlockCentre(moreList.get(0));
        moreList.get(0).click();
      }
    } while(i < 10 && moreList.size() > 0);
    for (String key : courses.keySet()) {
      allCourses.add(getCourse(Course.class, key, courses.get(key)));
    }
    return this;
  }

  public Course findCourseWithJSOUP(String courseName) {
    String currentCourseName = "//a/h6/div[contains(text(),\"" + "%s" + "\")]";
    String className = "sc-zzdkm7-0";
    String h6CSSQuery = "h6 > div";
    String cssQuery = "h6 + div > div > div";
    findCourse(courseName);
    By xpath = By.xpath(String.format(currentCourseName, courseName));
    waiter.waitForElementVisibleByLocator(xpath);
    try {
      String pageSource = driver.getPageSource();
      Document doc = Jsoup.parse(pageSource);
      Elements links = doc.getElementsByClass(className);
      for (Element element : links) {
        String jsoupCourse = element.select(h6CSSQuery).text();
        if (courseName.equals(jsoupCourse)) {
          String date = element.select(cssQuery).text();
          return getCourse(Course.class, jsoupCourse, date);
        }
      }
    } catch (Exception e) {
      System.out.println("Ошибка в JSOUP: " + e.getMessage());
    }
    return getCourse(Course.class, "", "");
  }
}
