package jeez.lang;

public class CustomType implements Type {

  private String name;
  
  public CustomType(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
