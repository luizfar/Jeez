package jeez.interpreter.execution.exception;

import jeez.lang.Method;

public class WrongNumberOfArgumentsException extends RuntimeException {

  private static final long serialVersionUID = 4698147383835489517L;

  public WrongNumberOfArgumentsException(Method method, int argumentsCount) {
    super("Method '" + method.getName() + "' of class '"
        + method.getOwner().getName() + "' expects "
        + method.getParametersCount() + " arguments but only " + argumentsCount
        + " were passed.");
  }
}
