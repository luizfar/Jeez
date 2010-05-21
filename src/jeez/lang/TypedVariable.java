package jeez.lang;


public class TypedVariable extends Variable {

  private Clazz clazz;
  
  public TypedVariable(Clazz clazz, String name) {
    super(name);
    this.clazz = clazz;
  }
  
  @Override
  public void setValue(JeezObject value) {
    if (!clazz.equals(value.getClazz())) {
      throw new RuntimeException("Can't assign value of class '" + value.getClazz().getName() + "' to variable of type '" + clazz.getName() + "'");
    }
    super.setValue(value);
  }
}
