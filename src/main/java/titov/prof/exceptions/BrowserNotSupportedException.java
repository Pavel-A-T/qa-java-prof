package titov.prof.exceptions;

public class BrowserNotSupportedException extends RuntimeException {
  public BrowserNotSupportedException(String browserName) {
    super(browserName + " - Неподдерживаемый браузер!");
  }
}
