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
import titov.prof.factory.settings.RemoteWebDriverFactory;

public class WebDriverFactory {
  private String browserName = System.getProperty("browser.name");
  private String remoteURL = System.getProperty("remote.url", "http://pavel:pavel@192.168.31.50:4444/wd/hub");
  private String browserVersion = System.getProperty("browser.version");

  public WebDriver getWebDriver() {
    if (!remoteURL.isEmpty()) {
      return createRemoteDriver();
    }

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

  private WebDriver createRemoteDriver() {
    RemoteWebDriverFactory remoteFactory = new RemoteWebDriverFactory(browserName, browserVersion, remoteURL);
    switch (browserName) {
      case "chrome": {
        ChromeOptions options = (ChromeOptions) new ChromeSettings().settings();
        return remoteFactory.createRemoteDriver(options);
      }
      default:
        throw new BrowserNotSupportedException(browserName);
    }
  }
}
