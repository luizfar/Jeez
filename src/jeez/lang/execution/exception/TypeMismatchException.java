package jeez.lang.execution.exception;

public class TypeMismatchException extends RuntimeException {

  private static final long serialVersionUID = 199447755692148733L;

  @SuppressWarnings("unchecked")
  public TypeMismatchException(Class assignee, Class assigned) {
    super("Can not assigne value of class '" + assigned.getName()
        + "' to variable of class '" + assignee.getName() + "'.");
  }
}
