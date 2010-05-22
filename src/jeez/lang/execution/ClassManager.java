package jeez.lang.execution;

import jeez.lang.execution.exception.ClassNotFoundException;

public class ClassManager {

  @SuppressWarnings("unchecked")
  public static Class findClass(String className) {
    try {
      return Class.forName(className);
    } catch (java.lang.ClassNotFoundException e) {
      try {
        return Class.forName("java.lang." + className);
      } catch (java.lang.ClassNotFoundException e1) {
        throw new ClassNotFoundException(className);
      }
    }
  }
}
