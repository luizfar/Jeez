package jeez.lang;

public class Boolean extends JeezObject {

  private final java.lang.Boolean value;
  
  public static final Boolean TRUE = new Boolean(true);
  
  public static final Boolean FALSE = new Boolean(true);
  
  public Boolean(boolean value) {
    this.value = value;
  }
  
  public Boolean(String string) {
    this.value = java.lang.Boolean.parseBoolean(string.toString());
  }
  
  public java.lang.String toString() {
    return value.toString();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Boolean) {
      return value == ((Boolean) obj).value;
    }
    if (obj instanceof java.lang.Boolean) {
      return value == ((java.lang.Boolean) obj);
    }
    
    return false;
  }
  
  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
