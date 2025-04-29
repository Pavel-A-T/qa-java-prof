package titov.prof.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import titov.prof.commons.annotaitions.Path;
import java.util.List;
import java.util.Optional;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {
  private By spanStudy = By.xpath("//span[contains(@title,\"Обучение\")]");
  private By categoryPath = By.xpath("//a[contains(@href,\"/categories/\")]");
  private By checkBoxPath = By.xpath("./ancestor::div[contains(@class, 'eQrMuA')]");

  public MainPage(WebDriver driver) {
    super(driver);
  }

  private By getLabel(String label) {
    return By.xpath("//label[contains(text(),\"" + label + "\")]");
  }

  public boolean isCheckedFinedElement(String text) {
    WebElement study = driver.findElement(spanStudy);
    actions.moveToElement(study).perform();
    List<WebElement> categories = driver.findElements(categoryPath);
    Optional<WebElement> link = categories.stream().filter(o -> {
      try {
        return o.getText().replaceAll("\\s*\\(\\d+\\)", "").trim().equals(text);
      } catch (Exception e) {
        return false;
      }
    })
        .findFirst();
    if (link.isPresent()) {
      WebElement element = link.get();
      element.click();
    }
    WebElement label = driver.findElement(getLabel(text));
    WebElement checkBox = label.findElement(checkBoxPath);
    String valueAttribute = checkBox.getDomAttribute("value");
    return (valueAttribute != null && valueAttribute.equals("true"));
  }
}
