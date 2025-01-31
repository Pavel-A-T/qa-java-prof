package titov.prof.extentions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import titov.prof.guice.GuiceModule;

public class UIExtention implements BeforeEachCallback, AfterEachCallback {
  private Injector injector;

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    injector = Guice.createInjector(new GuiceModule());
    injector.injectMembers(extensionContext.getTestInstance().get());
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) {
    WebDriver driver = injector.getInstance(WebDriver.class);
    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }
}
