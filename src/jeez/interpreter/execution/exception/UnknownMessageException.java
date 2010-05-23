package jeez.interpreter.execution.exception;

import jeez.lang.JeezObject;

public class UnknownMessageException extends RuntimeException {

  private static final long serialVersionUID = -7540865694439999239L;
  
  private JeezObject target;
  
  private String messageName;
  
  public UnknownMessageException(JeezObject target, String messageName) {
    this.target = target;
    this.messageName = messageName;
  }
  
  @Override
  public String getMessage() {
    return "Object of class '" + target.getJeezClass().getName() + "' does not understand message '" + messageName + "'.";
  }
}
