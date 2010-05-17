package jeez.lang.builder;

import static jeez.lang.Type.DUCK;

import java.util.List;

import jeez.lang.ClassMethod;
import jeez.lang.Clazz;
import jeez.lang.JeezObject;
import jeez.lang.Method;
import jeez.lang.bootstrap.Classes;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;
import jeez.lang.java.JeezBoolean;
import jeez.lang.java.JeezString;

public class ClassBuilder {

  private static ClassBuilder instance = new ClassBuilder();
  
  private ClassBuilder() {
    
  }
  
  public static ClassBuilder get() {
    return instance;
  }
  
  
  public Clazz buildClass(String name) {
    final Clazz clazz = new Clazz(name);
    
    Method implicitInitializer = new Method(clazz, DUCK, "new") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return super.execute(target, arguments, context);
      }
    };
    
    Method getClass = new Method(clazz, DUCK, "getClass") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return clazz;
      }
    };
    
    Method understands = new Method(clazz, DUCK, "understands") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        String methodName = arguments.get(0).evaluate(context).toString();
        return new JeezBoolean(target.getClazz().getMethod(methodName) != null);
      }
    };
    
    clazz.addToMethods(understands);
    clazz.addToMethods(getClass);
    clazz.addToMethods(implicitInitializer);
    
    ClassMethod getName = new ClassMethod(clazz, DUCK, "getName") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return new JeezString(clazz.getName());
      }
    };
    
    ClassMethod newObject = new ClassMethod(clazz, clazz, "new") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        JeezObject newObject = new JeezObject(clazz);
        newObject.receiveMessage("new", arguments, context);
        return newObject;
      }
    };
    
    ClassMethod getClassesClass = new ClassMethod(clazz, DUCK, "getClass") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        return Classes.CLASS;
      }
    };
    
    clazz.addToClassMethods(getName);
    clazz.addToClassMethods(newObject);
    clazz.addToClassMethods(getClassesClass);
    
    return clazz;
  }
}
