package titov.prof.factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class ChromeSettings implements IBrowserSettings {
  @Override
  public AbstractDriverOptions settings() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--disable-extensions");
    chromeOptions.addArguments("--start-maximized");
    return  chromeOptions;
  }
}
