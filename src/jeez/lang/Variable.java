package jeez.lang;

public class Variable {

  private String name;
  
  private JeezObject value;
  
  public Variable(String name) {
    this.name = name;
  }
  
  public Variable(String name, JeezObject value) {
    this.name = name;
    this.value = value;
  }
  
  public String getName() {
    return name;
  }
  
  public void setValue(JeezObject value) {
    this.value = value;
  }
  
  public JeezObject getValue() {
    return value;
  }
}
