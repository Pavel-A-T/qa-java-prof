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
  List<Course> allCourses = new ArrayList<>();
  @FindBy(xpath = "//input[@type=\"search\"]")
  private WebElement search;
  By linkWithClass = By.className("sc-zzdkm7-0");
  By pathButtonSeeMore = By.xpath("//button[contains(text(),\"Показать еще\")]");
  By courseDate = By.xpath("./div/div/div[contains(@class,\"jEGzDf\") and not(*)]");
  By courseNamePath = By.xpath("./h6/div");

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
      optional.get().click();
      return page(LessonPage.class);
    }
    throw new RuntimeException(courseName + " не найден!");
  }

  public CoursePage getAllCourses() {
    Map<String, String> courses = new HashMap<>();
    List<WebElement> moreList;
    do {
      List<WebElement> elements = driver.findElements(linkWithClass);
      elements
          .stream()
          .filter(o -> {
            try {
              String value = o.findElement(courseDate).getText();
              String key = o.findElement(courseNamePath).getText();
              courses.put(key, value);
              return true;
            } catch (Exception ignored) {
              return false;
            }
          });
      moreList = driver.findElements(pathButtonSeeMore);
      if (moreList.size() == 1) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", moreList.get(0));
        moreList.get(0).click();
      }
    } while(moreList.size() > 0);
    for (String key : courses.keySet()) {
      allCourses.add(getCourse(Course.class, key, courses.get(key)));
    }
    return this;
  }

  public Course findCourseWithJSOUP(String courseName) {
    String className = "sc-zzdkm7-0";
    String h6CSSQuery = "h6 > div";
    String cssQuery = "h6 + div > div > div";
    List<WebElement> moreList;
    try {
      do {
        moreList = driver.findElements(pathButtonSeeMore);
        if (moreList.size() == 1) {
          moreList.get(0).click();
        }
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
      } while (moreList.size() > 0);
    } catch (Exception e) {
      System.out.println("Ошибка в JSOUP: " + e.getMessage());
    }
    return null;
  }
}
