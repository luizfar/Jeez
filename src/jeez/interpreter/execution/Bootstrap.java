package jeez.interpreter.execution;

import static jeez.lang.Constants.NEW;
import static jeez.lang.JeezBoolean.FALSE;
import static jeez.lang.JeezBoolean.TRUE;

import java.util.List;

import jeez.lang.JeezBoolean;
import jeez.lang.JeezClass;
import jeez.lang.JeezInteger;
import jeez.lang.JeezObject;
import jeez.lang.JeezString;
import jeez.lang.Method;
import jeez.lang.TypedVariable;
import jeez.lang.expression.Expression;

public class Bootstrap {
  
  private static ClassBuilder CLASS_BUILDER = new ClassBuilder();
  
  public static JeezBoolean BOOLEAN;
  
  public static JeezInteger INTEGER;
  
  public static JeezString STRING;
  
  public static JeezClass OBJECT;
  
  public static JeezClass CLASS;
  
  public static JeezClass getBooleanClass() {
    if (BOOLEAN == null) {
      BOOLEAN = new JeezBoolean();
      BOOLEAN.setSuperClass(OBJECT);
    }
    return BOOLEAN;
  }
  
  public static JeezClass getIntegerClass() {
    if (INTEGER == null) {
      INTEGER = new JeezInteger();
      INTEGER.setSuperClass(OBJECT);
    }
    return INTEGER;
  }
  
  public static JeezClass getStringClass() {
    if (STRING == null) {
      STRING = new JeezString();
      STRING.setSuperClass(OBJECT);
    }
    return STRING;
  }
  
  public static JeezClass getObjectClass() {
    if (OBJECT == null) {
      OBJECT = new JeezClass("Object");
      OBJECT.setSuperClass(OBJECT);
      
      Method newObject = new Method(OBJECT, OBJECT, "new") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          return new JeezObject(OBJECT);
        }
      };
      
      Method showMethods = new Method(OBJECT, STRING, "showMethods") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          StringBuilder builder = new StringBuilder();
          for (Method m : target.getMethods()) {
            if (builder.length() != 0) {
              builder.append(", ");
            }
            builder.append(m.getName());
          }
          
          JeezClass clazz = target.getJeezClass();
          while (clazz != clazz.getSuperClass()) {
            for (Method m : clazz.getClassMethods()) {
              if (builder.length() != 0) {
                builder.append(", ");
              }
              builder.append(m.getName());
            }
            clazz = clazz.getSuperClass();
          }
          for (Method m : clazz.getClassMethods()) {
            if (builder.length() != 0) {
              builder.append(", ");
            }
            builder.append(m.getName());
          }
          
          return new JeezString.String(builder.toString());
        }
      };
      
      Method understands = new Method(OBJECT, BOOLEAN, "understands") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          String messageName = ((JeezString.String) arguments.get(0).evaluate(context)).toString();
          Method method = target.findMethod(messageName);
          return method == null ? FALSE : TRUE; 
        }
      };
      understands.addToParameters(new TypedVariable(getStringClass(), "message"));
      
      Method equals = new Method(OBJECT, BOOLEAN, "equals") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          JeezObject another = arguments.get(0).evaluate(context);
          return target.equals(another) ? TRUE : FALSE; 
        }
      };
      equals.addToParameters(new TypedVariable(OBJECT, "another"));

      
      OBJECT.addToMethods(newObject);
      OBJECT.addToClassMethods(showMethods);
      OBJECT.addToClassMethods(understands);
      OBJECT.addToClassMethods(equals);
    }
    return OBJECT;
  }
  
  public static JeezClass getClassClass() {
    if (CLASS == null) {
      CLASS = CLASS_BUILDER.build("Class");
      
      Method newClass = new Method(CLASS, CLASS, NEW) {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments,ExecutionContext context) {
          String name = ((JeezString.String) arguments.get(0).evaluate(context)).toString();
          return CLASS_BUILDER.build(name);
        }
      };
      newClass.addToParameters(new TypedVariable(getStringClass(), "name"));
      
      CLASS.addToMethods(newClass);
    }
    
    return CLASS;
  }
}
