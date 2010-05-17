package jeez.lang;

public class Attribute {

  private Type type;
  
  private String name;
  
  public Attribute(Type type, String name) {
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
