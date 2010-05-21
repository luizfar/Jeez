package jeez.lang;

public class UnknownMessageException extends RuntimeException {

  private static final long serialVersionUID = -1943413456447278510L;

  private Clazz clazz;
  
  private String variableName;
  
  private String messageName;
  
  public UnknownMessageException(Clazz clazz, String messageName) {
    this.clazz = clazz;
    this.messageName = messageName;
  }
  
  public void setVariableName(String variableName) {
    this.variableName = variableName;
  }
  
  @Override
  public String getMessage() {
    return "Object" + (variableName != null ? " '" + variableName + "' " : " ")
        + "of class '" + clazz.getName() + "' does not understand message '"
        + messageName + "'";
  }
}
