package titov.prof.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LessonPage extends AbsBasePage<LessonPage> {
  public LessonPage(WebDriver webDriver) {
    super(webDriver);
  }

  public String getHeaderCourse() {
    By headerLocator = By.cssSelector("h1");
    WebElement header = byLocator(headerLocator);
    if (waiter.waitForCondition(ExpectedConditions.visibilityOf(header))) {
      return header.getText();
    }
    throw new RuntimeException("This webElement header is not visible");
  }
}
