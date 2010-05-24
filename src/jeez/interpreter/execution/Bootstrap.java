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
  
  private static JeezBoolean BOOLEAN;
  
  private static JeezInteger INTEGER;
  
  private static JeezString STRING;
  
  private static JeezClass OBJECT;
  
  private static JeezClass MODULE;
  
  public static JeezClass CLASS = getClassClass();
  
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
  
  public static JeezClass getModuleClass() {
    if (MODULE == null) {
      MODULE = CLASS_BUILDER.build("Module");
      MODULE.setSuperClass(OBJECT);
    }
    return MODULE;
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
      
      Method getClass = new Method(OBJECT, getClassClass(), "getClass") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          return target.getJeezClass();
        }
      };
      
      OBJECT.addToMethods(newObject);
      OBJECT.addToClassMethods(showMethods);
      OBJECT.addToClassMethods(understands);
      OBJECT.addToClassMethods(equals);
      OBJECT.addToClassMethods(getClass);
    }
    return OBJECT;
  }
  
  public static JeezClass getClassClass() {
    if (CLASS == null) {
      CLASS = CLASS_BUILDER.build("Class");
      CLASS.init();
      CLASS.setSuperClass(getObjectClass());
      
      Method newClass = new Method(CLASS, CLASS, NEW) {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments,ExecutionContext context) {
          String name = ((JeezString.String) arguments.get(0).evaluate(context)).toString();
          JeezClass clazz = CLASS_BUILDER.build(name);
          context.addClass(clazz);
          
          return clazz;
        }
      };
      newClass.addToParameters(new TypedVariable(getStringClass(), "name"));
      
      Method getName = new Method(CLASS, getStringClass(), "getName") {
        @Override
        public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
          JeezClass clazz = (JeezClass) target;
          return new JeezString.String(clazz.getName());
        }
      };
      
      CLASS.addToMethods(newClass);
      CLASS.addToClassMethods(getName);
    }
    
    return CLASS;
  }
}
