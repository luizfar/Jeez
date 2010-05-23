package jeez.interpreter.execution.exception;

import jeez.lang.JeezClass;

public class TypeMismatchException extends RuntimeException {

  private static final long serialVersionUID = 199447755692148733L;

  public TypeMismatchException(JeezClass assignee, JeezClass assigned) {
    super("Can not assigne value of class '" + assigned.getName()
        + "' to variable of class '" + assignee.getName() + "'.");
  }
}
