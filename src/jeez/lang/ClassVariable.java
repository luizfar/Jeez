package jeez.lang;

public class ClassVariable extends Variable {

  @SuppressWarnings("unchecked")
  public ClassVariable(Class clazz) {
    super(clazz.getName());
    setValue(clazz);
  }
}
