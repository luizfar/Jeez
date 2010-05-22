package jeez.interpreter.load;

import jeez.lang.Attribute;
import jeez.lang.Clazz;
import jeez.lang.Method;

@SuppressWarnings("unchecked")
public interface ClassCreator {

  public void startClassCreation(Clazz clazz);

  public void createAttribute(Attribute attr);

  public void createMethod(Method method);

  public Class finishClassCreation(Clazz clazz);
}
