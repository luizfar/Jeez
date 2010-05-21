package jeez.lang;

public class ClassVariable extends Variable {

  public ClassVariable(Clazz clazz) {
    super(clazz.getName());
    setValue(clazz);
  }
}
