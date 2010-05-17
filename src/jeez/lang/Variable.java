package jeez.lang;

public abstract class Variable implements MessageReceiver {

  private String name;
  
  private JeezObject value;
  
  public Variable(String name) {
    this.name = name;
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
