package jeez.lang.execution;

import static jeez.lang.Clazz.NEW;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import jeez.lang.execution.exception.UnknownMessageException;

public class MethodFinder {

  @SuppressWarnings("unchecked")
  public Method findMethod(Class clazz, String methodName, Object... arguments) {
    outerFor: for (Method method : clazz.getMethods()) {
      if (methodName.equals(method.getName())) {
        Class[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != arguments.length) {
          continue;
        }
        for (int i = 0; i < paramTypes.length; i++) {
          Class paramType = paramTypes[i];
          if (!paramType.isAssignableFrom(arguments[i].getClass())) {
            continue outerFor;
          }
        }
        return method;
      }
    }
    throw new UnknownMessageException(clazz, methodName);
  }
  
  @SuppressWarnings("unchecked")
  public Constructor findConstructor(Class clazz, Object... arguments) {
    outerFor: for (Constructor constructor : clazz.getConstructors()) {
      Class[] paramTypes = constructor.getParameterTypes();
      if (paramTypes.length != arguments.length) {
        continue;
      }
      for (int i = 0; i < paramTypes.length; i++) {
        Class paramType = paramTypes[i];
        if (!paramType.isAssignableFrom(arguments[i].getClass())) {
          continue outerFor;
        }
      }
      return constructor;
    }
    throw new UnknownMessageException(clazz, NEW);
  }
}
