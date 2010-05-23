package jeez.interpreter.execution;

import static jeez.interpreter.execution.Bootstrap.getObjectClass;

import java.util.List;

import jeez.lang.JeezClass;
import jeez.lang.JeezObject;
import jeez.lang.Method;
import jeez.lang.expression.Expression;

public class ClassBuilder {
  
  public JeezClass build(String name) {
    final JeezClass clazz = new JeezClass(name);
    clazz.setSuperClass(getObjectClass());
    
    Method newObject = new Method(clazz, clazz, "new") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return new JeezObject(clazz);
      }
    };
    
    clazz.addToMethods(newObject);
    
    return clazz;
  }
}
