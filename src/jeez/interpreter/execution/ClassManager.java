package jeez.interpreter.execution;

import jeez.interpreter.execution.exception.ClassNotFoundException;

public class ClassManager {

  @SuppressWarnings("unchecked")
  public static Class findClass(String className) {
    try {
      return Class.forName(className);
    } catch (java.lang.ClassNotFoundException e) {
      try {
        return Class.forName("jeez.lang." + className);
      } catch (java.lang.ClassNotFoundException e1) {
        try {
          return Class.forName("java.lang." + className);
        } catch (java.lang.ClassNotFoundException e2) {
          throw new ClassNotFoundException(className);
        }
      }
    }
  }
}
