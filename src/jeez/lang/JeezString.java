package jeez.lang;

import static jeez.interpreter.execution.Bootstrap.getStringClass;

import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.expression.Expression;

public class JeezString extends JeezClass {

  public JeezString() {
    super("String");
    
    Method _add = new Method(this, this, "_add") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        String self = (String) target; 
        
        JeezObject obj = arguments.get(0).evaluate(context);
        String string = (String) obj;
        return new String(self.value + string.value);
      }
    };
    _add.addToParameters(new TypedVariable(this, "string"));
    
    addToClassMethods(_add);
  }
  
  public static String newValue(java.lang.String value) {
    return new String(value);
  }
  
  public static class String extends JeezObject {
    
    private java.lang.String value;
    
    public String(java.lang.String value) {
      super(getStringClass());
      this.value = value;
    }
    @Override
    public java.lang.String toString() {
      return value;
    }
    @Override
    public int hashCode() {
      return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof String) {
        return value.equals(((String) obj).value);
      }
      if (obj instanceof java.lang.String) {
        return value.equals((java.lang.String) obj);
      }
      
      return false;
    }
  }
}
