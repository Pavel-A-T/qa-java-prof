package titov.prof.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import titov.prof.commons.waiters.Waiter;

public abstract class AbsCommon {
  protected WebDriver driver;
  protected Waiter waiter;
  protected Actions actions;

  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.waiter = new Waiter(this.driver);
    this.actions = new Actions(driver);
    PageFactory.initElements(driver, this);
  }

  protected void goBlockCentre(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
  }

  public WebElement byLocator(By locator) {
    return driver.findElement(locator);
  }
}
