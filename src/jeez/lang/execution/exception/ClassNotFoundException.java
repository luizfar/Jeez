package jeez.lang.execution.exception;

public class ClassNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5641794371354980110L;

  public ClassNotFoundException(String className) {
    super("Unknown class: " + className);
  }
}
