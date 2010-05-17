package jeez.lang.java;

import jeez.lang.JeezObject;

public class JeezBoolean extends JeezObject {

  private boolean value;
  
  public static final JeezBoolean TRUE = new JeezBoolean(true);
  
  public static final JeezBoolean FALSE = new JeezBoolean(false);
  
  public JeezBoolean(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }
  
  public String toString() {
    return String.valueOf(value);
  }
  
  public boolean equals(Object o) {
    if (o instanceof JeezBoolean) {
      return value == ((JeezBoolean) o).getValue();
    }
    
    if (o instanceof Boolean) {
      return value == ((Boolean) o).booleanValue();
    }
    
    return false;
  }
}
