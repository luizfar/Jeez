package jeez.lang;

public class Attribute {

  private Clazz type;
  
  private String name;
  
  public Attribute(Clazz type, String name) {
    this.type = type;
    this.name = name;
  }

  public Clazz getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
