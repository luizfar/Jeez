package jeez.lang;

import jeez.interpreter.execution.exception.TypeMismatchException;

public class TypedVariable extends Variable {

  private JeezClass clazz;
  
  public TypedVariable(JeezClass clazz, String name) {
    super(name);
    this.clazz = clazz;
  }
  
  public void setValue(JeezObject value) {
    if (clazz.isAssignableFrom(value.getJeezClass())) {
      super.setValue(value);
    } else {
      throw new TypeMismatchException(clazz, value.getJeezClass());
    }
  }
}
