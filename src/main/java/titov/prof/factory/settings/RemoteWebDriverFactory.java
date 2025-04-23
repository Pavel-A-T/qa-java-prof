package titov.prof.factory.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class RemoteWebDriverFactory {
  private String browserName = "";
  private String browserVersion = "";
  private String remoteURL = "";

  public RemoteWebDriverFactory(String browserName, String browserVersion, String remoteURL) {
    this.browserName = browserName;
    this.browserVersion = browserVersion;
    this.remoteURL = remoteURL;
  }

  public WebDriver createRemoteDriver(AbstractDriverOptions<?> options) {
    options.setCapability("browserVersion", browserVersion);
    options.setCapability("browserName", browserName);
    options.setCapability("selenoid:options", new HashMap<String, Object>() {{
        put("name", "This is Otus webpage test");
        put("sessionTimeout", "15m");
        put("enableVNC", true);
        put("enableVideo", false);
      }});
    try {
      return new RemoteWebDriver(new URL(remoteURL), options);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Invalid remote URL: " + remoteURL, e);
    }
  }
}
