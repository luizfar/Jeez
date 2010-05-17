package jeez.lang.bootstrap;

import java.util.List;

import jeez.lang.ClassMethod;
import jeez.lang.Clazz;
import jeez.lang.JeezObject;
import jeez.lang.Method;
import jeez.lang.builder.ClassBuilder;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Classes {

  public static final Clazz CLASS = ClassBuilder.get().buildClass("Class");
  
  static {
    Method newObject = new ClassMethod(CLASS, CLASS, "new") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return ClassBuilder.get().buildClass(arguments.get(0).evaluate(context).toString());
      }
    };
    CLASS.addToClassMethods(newObject);
  }
}
