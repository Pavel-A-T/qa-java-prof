package titov.prof.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import titov.prof.factory.WebDriverFactory;
import titov.prof.pages.CoursePage;
import titov.prof.pages.MainPage;
import titov.prof.utils.ReduceCourses;

public class GuiceModule extends AbstractModule {
  private WebDriver driver;

  public GuiceModule() {
    this.driver = new WebDriverFactory().getWebDriver();
  }

  @Provides
  private WebDriver getWebDriver() {
    return this.driver;
  }

  @Singleton
  @Provides
  public MainPage getMainPage() {
    return new MainPage(driver);
  }

  @Provides
  public CoursePage getCoursePage() {
    return new CoursePage(driver);
  }

  @Singleton
  @Provides
  public ReduceCourses getReduceCourses() {
    return new ReduceCourses();
  }
}
