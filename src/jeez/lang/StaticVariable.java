package jeez.lang;

public class StaticVariable extends Variable {

  private Clazz type;
  
  public StaticVariable(Clazz type, String name) {
    super(name);
  }
  
  public Clazz getType() {
    return type;
  }
}
