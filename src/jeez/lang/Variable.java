package jeez.lang;

public abstract class Variable {

  private String name;
  
  private Object value;
  
  public Variable(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setValue(Object value) {
    this.value = value;
  }
  
  public Object getValue() {
    return value;
  }
}
