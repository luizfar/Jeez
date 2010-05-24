package jeez.interpreter.execution.exception;

public class UnknownVariableException extends RuntimeException {
  
  private static final long serialVersionUID = 6030091456220310992L;

  public UnknownVariableException(String variableName) {
    super("Could not find variable '" + variableName + "'.");
  }
}
