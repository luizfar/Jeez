package jeez.interpreter.execution;

import java.util.HashMap;
import java.util.Map;

import jeez.interpreter.execution.exception.UnknownMessageException;

@SuppressWarnings("unchecked")
public class JeezMethodsInvoker {

  private Map<String, JeezMethod> objectMethods = new HashMap<String, JeezMethod>();
  
  public JeezMethodsInvoker() {
    objectMethods.put("understands", new UnderstandsMethod());
  }
  
  public boolean isJeezMethod(Class clazz, String methodName) {
    if (clazz == Class.class) {
      return false;
    }
    return objectMethods.containsKey(methodName);
  }
  
  public Object doInvoke(Object target, String methodName, Object... arguments) {
    JeezMethod method = objectMethods.get(methodName);
    if (method == null) {
      throw new UnknownMessageException(target.getClass(), methodName);
    }
    
    return method.invoke(target, methodName, arguments);
  }
  
  private static interface JeezMethod {
    Object invoke(Object target, String methodName, Object... arguments);
  }
  
  private static class UnderstandsMethod implements JeezMethod {
    @Override
    public Object invoke(Object target, String methodName, Object... arguments) {
      // TODO create appropriate exceptions
      if (arguments.length > 1) {
        throw new RuntimeException("Wrong number of arguments.");
      }
      if (!(arguments[0] instanceof String)) {
        throw new RuntimeException("Wrong argument type.");
      }
      
      try {
        return target.getClass().getMethod((String) arguments[0]) != null;
      } catch (SecurityException e) {
        return false;
      } catch (NoSuchMethodException e) {
        return false;
      }
    }
  }
}
