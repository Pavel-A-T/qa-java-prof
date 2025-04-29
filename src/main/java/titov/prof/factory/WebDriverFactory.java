package titov.prof.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import titov.prof.exceptions.BrowserNotSupportedException;
import titov.prof.factory.settings.ChromeSettings;
import titov.prof.factory.settings.FirefoxSettings;

public class WebDriverFactory {
  private String browserName = System.getProperty("browser.name");

  public WebDriver getWebDriver() {
    switch (browserName) {
      case "chrome": {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
      }
      case "firefox": {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
      }
    }
    throw new BrowserNotSupportedException(browserName);
  }
}
