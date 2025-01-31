package titov.prof.commons.waiters;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waiter {
  private WebDriverWait waiter;

  public Waiter(WebDriver webDriver) {
    long time = Long.valueOf(System.getProperty("webdriver.waiter.timeout"));
    long duration = Long.valueOf(System.getProperty("webdriver.waiter.duration"));
    this.waiter = new WebDriverWait(webDriver, Duration.ofSeconds(time));
    waiter.pollingEvery(Duration.ofSeconds(duration));
  }

  public boolean waitForCondition(ExpectedCondition condition) {
    try {
      waiter.until(condition);
      return true;
    } catch (TimeoutException ignored) {
      return false;
    }
  }

  public boolean waitForElementVisibleByLocator(By locator) {
    return this.waitForCondition(ExpectedConditions.visibilityOfElementLocated(locator));
  }
}
