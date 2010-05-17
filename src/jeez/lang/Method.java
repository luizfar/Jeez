package jeez.lang;

public class Method extends Function {

  private Clazz owner;
  
  public Method(Clazz owner, Clazz type, String name) {
    super(type, name);
  }
  
  public Clazz getOwner() {
    return owner;
  }
}
