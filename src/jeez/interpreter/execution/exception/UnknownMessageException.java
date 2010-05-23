package jeez.interpreter.execution.exception;

public class UnknownMessageException extends RuntimeException {

  private static final long serialVersionUID = -7540865694439999239L;

  private String messageName;
  
  public UnknownMessageException(String messageName) {
    this.messageName = messageName;
  }
  
  @Override
  public String getMessage() {
    return "Object does not understand message '" + messageName + "'.";
  }
}
