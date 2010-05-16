package jeez.lang;

public class Method extends Function {

  private Clazz owner;
  
  public Method(Clazz owner, Clazz type, String name, Block block) {
    super(type, name, block);
  }
  
  public Clazz getOwner() {
    return owner;
  }
}
