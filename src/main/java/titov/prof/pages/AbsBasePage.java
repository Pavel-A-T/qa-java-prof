package titov.prof.pages;

import org.openqa.selenium.WebDriver;
import titov.prof.commons.annotaitions.Path;
import titov.prof.exceptions.PathPageException;
import titov.prof.commons.AbsCommon;
import titov.prof.utils.Course;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbsBasePage<T> extends AbsCommon {
  private String baseURL = System.getProperty("base.url");

  public AbsBasePage(WebDriver webDriver) {
    super(webDriver);
    baseURL = baseURL.endsWith("/") ? baseURL.substring(0, baseURL.length() - 1) : baseURL;
  }

  private String getPath() {
    Class<? extends AbsBasePage> clazz = getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = clazz.getDeclaredAnnotation(Path.class);
      return path.value().startsWith("/") ? path.value() : "/" + path.value();
    }
    else {
      return "";
    }
  }

  public <V extends AbsBasePage> V page(Class<? extends AbsBasePage> clazz) {
    Constructor<? extends AbsBasePage> constructor;
    try {
      constructor = clazz.getConstructor(WebDriver.class);
      return (V) constructor.newInstance(driver);
    }
    catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public Course getCourse(Class clazz, String nameCourse, String startDate) {
    try {
      Constructor constructor = clazz.getDeclaredConstructor(String.class, String.class);
      return (Course) constructor.newInstance(nameCourse, startDate);
    }
    catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public T open() {
    String path = getPath();
    if (path.isEmpty()) {
      throw new PathPageException();
    }
    driver.get(baseURL + path);
    return (T) this;
  }
}
