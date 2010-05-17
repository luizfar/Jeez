package jeez.lang.java;

import jeez.lang.JeezObject;

public class JeezBoolean extends JeezObject {

  private boolean value;
  
  public JeezBoolean(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }
  
  public String toString() {
    return String.valueOf(value);
  }
}
