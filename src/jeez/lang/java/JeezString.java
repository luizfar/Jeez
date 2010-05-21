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
  
  public boolean equals(Object o) {
    if (o instanceof JeezString) {
      return value.equals(((JeezString) o).value); 
    }
    
    if (o instanceof String) {
      return value.equals((String)o);
    }
    
    return false;
  }
}
