package jeez.lang;

import jeez.interpreter.execution.exception.TypeMismatchException;

@SuppressWarnings("unchecked")
public class TypedVariable extends Variable {

  private Class clazz;
  
  public TypedVariable(Class clazz, String name) {
    super(name);
    this.clazz = clazz;
  }
  
  public void setValue(Object value) {
    if (clazz.isAssignableFrom(value.getClass())) {
      super.setValue(value);
    } else {
      throw new TypeMismatchException(clazz, value.getClass());
    }
  }
}
