package jeez.interpreter.execution;

import jeez.lang.JeezClass;

public class ClassBuilder {

  public JeezClass build(String name) {
    JeezClass clazz = new JeezClass(name);
    return clazz;
  }
}
