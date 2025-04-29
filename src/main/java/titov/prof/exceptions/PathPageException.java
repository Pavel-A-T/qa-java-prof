package titov.prof.exceptions;

public class PathPageException extends RuntimeException {
  public PathPageException() {
    super("Path on page not found!");
  }
}
