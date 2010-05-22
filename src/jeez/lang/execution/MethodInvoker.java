package jeez.lang.execution;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jeez.lang.execution.exception.UnknownMessageException;

public class MethodInvoker {
  
  private MethodFinder methodFinder = new MethodFinder();

  @SuppressWarnings("unchecked")
  public Object invoke(Object target, String messageName, Object[] arguments) {
    if (target.getClass() == Class.class && "new".equals(messageName)) {
      return createObject((Class) target, arguments);
    }
    
    Method method = methodFinder.findMethod(target.getClass(), messageName, arguments);

    try {
      return method.invoke(target, arguments);
    } catch (IllegalArgumentException e) {
      throw new UnknownMessageException(target.getClass(), messageName);
    } catch (IllegalAccessException e) {
      throw new UnknownMessageException(target.getClass(), messageName);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    }
  }
  
  @SuppressWarnings("unchecked")
  private Object createObject(Class clazz, Object[] arguments) {
    String messageName = "new";
    Constructor constructor = methodFinder.findConstructor(clazz, arguments);

    try {
      return constructor.newInstance(arguments);
    } catch (IllegalArgumentException e) {
      throw new UnknownMessageException(clazz, messageName);
    } catch (IllegalAccessException e) {
      throw new UnknownMessageException(clazz, messageName);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getCause());
    } catch (InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getCause());
    }
  }
}
