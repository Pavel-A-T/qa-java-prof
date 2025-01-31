package titov.prof.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import titov.prof.commons.waiters.Waiter;

public abstract class AbsCommon {
  protected WebDriver driver;
  protected Waiter waiter;

  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.waiter = new Waiter(this.driver);
    PageFactory.initElements(driver, this);
  }

  public WebElement byLocator(By locator) {
    return driver.findElement(locator);
  }
}
