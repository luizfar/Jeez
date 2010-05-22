package jeez.lang.execution;

import static jeez.lang.Clazz.NEW;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jeez.lang.execution.exception.UnknownMessageException;

public class MethodInvoker {
  
  private JeezMethodsInvoker jeezMethodsInvoker = new JeezMethodsInvoker();
  
  private MethodFinder methodFinder = new MethodFinder();

  @SuppressWarnings("unchecked")
  public Object invoke(Object target, String methodName, Object[] arguments) {
    Class clazz = target.getClass();
    
    if (clazz == Class.class && NEW.equals(methodName)) {
      return createObject((Class) target, arguments);
    } else if (jeezMethodsInvoker.isJeezMethod(clazz, methodName)) {
      return jeezMethodsInvoker.doInvoke(target, methodName, arguments);
    }
    
    Method method = methodFinder.findMethod(clazz, methodName, arguments);

    try {
      return method.invoke(target, arguments);
    } catch (IllegalArgumentException e) {
      throw new UnknownMessageException(clazz, methodName);
    } catch (IllegalAccessException e) {
      throw new UnknownMessageException(clazz, methodName);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    }
  }
  
  @SuppressWarnings("unchecked")
  private Object createObject(Class clazz, Object[] arguments) {
    Constructor constructor = methodFinder.findConstructor(clazz, arguments);

    try {
      return constructor.newInstance(arguments);
    } catch (IllegalArgumentException e) {
      throw new UnknownMessageException(clazz, NEW);
    } catch (IllegalAccessException e) {
      throw new UnknownMessageException(clazz, NEW);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getCause());
    } catch (InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getCause());
    }
  }
}
