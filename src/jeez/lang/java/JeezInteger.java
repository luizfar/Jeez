package jeez.lang.java;

public class JeezInteger extends JeezNumber {

  private int value;
  
  public JeezInteger(int value) {
    this.value = value;
  }
  
  public String toString() {
    return Integer.toString(value);
  }
  
  public int getValue() {
    return value;
  }
  
  public JeezNumber add(JeezNumber number) {
    if (number instanceof JeezInteger) {
      return new JeezInteger(value + ((JeezInteger) number).getValue());
    }
    return null;
  }
  
  public JeezNumber sub(JeezNumber number) {
    if (number instanceof JeezInteger) {
      return new JeezInteger(value - ((JeezInteger) number).getValue());
    }
    return null;
  }
  
  public boolean equals(Object o) {
    if (o instanceof JeezInteger) {
      return value == ((JeezInteger) o).value;
    }
    
    return false;
  }
}
