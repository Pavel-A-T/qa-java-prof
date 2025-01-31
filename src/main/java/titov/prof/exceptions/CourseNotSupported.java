package titov.prof.exceptions;

public class CourseNotSupported extends RuntimeException {
  public CourseNotSupported() {
    super("Course didn't save!");
  }
}
