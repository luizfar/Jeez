package jeez.lang.java;

import jeez.lang.JeezObject;

public class JeezString extends JeezObject {

  private String value;
  
  public JeezString(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }
  
  public String toString() {
    return value;
  }
}
