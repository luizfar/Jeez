package jeez.lang;

public class Integer implements Comparable<Integer> {

  private final int value;
  
  public Integer(int value) {
    this.value = value;
  }
  
  public Integer(String string) {
    this.value = java.lang.Integer.parseInt(string);
  }
  
  public Integer _add(Integer i) {
    return new Integer(value + i.value);
  }
  
  public Integer _minus(Integer i) {
    return new Integer(value - i.value);
  }
  
  public Integer _times(Integer i) {
    return new Integer(value * i.value);
  }
  
  public String toString() {
    return java.lang.Integer.toString(value);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Integer) {
      return value == ((Integer) obj).value;
    }
    if (obj instanceof java.lang.Integer) {
      return value == ((java.lang.Integer) obj).intValue();
    }
    
    return false;
  }
  
  @Override
  public int hashCode() {
    return value;
  }
  
  @Override
  public int compareTo(Integer anotherInteger) {
    return value < anotherInteger.value ? -1 : (value == anotherInteger.value ? 0 : 1);
  }
}
