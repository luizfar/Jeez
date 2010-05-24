package jeez.lang;

import static jeez.interpreter.execution.Bootstrap.getIntegerClass;

import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.expression.Expression;

public class JeezInteger extends JeezClass {
  
  public JeezInteger() {
    super("Integer");
    
    Method _add = new Method(this, this, "_add") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        Integer self = (Integer) target; 
        Integer i = (Integer) arguments.get(0).evaluate(context);
        return new Integer(self.value + i.value);
      }
    };
    _add.addToParameters(new TypedVariable(this, "i"));
    
    Method _minus = new Method(this, this, "_minus") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        Integer self = (Integer) target; 
        Integer i = (Integer) arguments.get(0).evaluate(context);
        return new Integer(self.value - i.value);
      }
    };
    _minus.addToParameters(new TypedVariable(this, "i"));
    
    Method _times = new Method(this, this, "_times") {
      @Override
      public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
        Integer self = (Integer) target; 
        Integer i = (Integer) arguments.get(0).evaluate(context);
        return new Integer(self.value * i.value);
      }
    };
    _times.addToParameters(new TypedVariable(this, "i"));
    
    addToClassMethods(_add);
    addToClassMethods(_minus);
    addToClassMethods(_times);
  }
  
  public static class Integer extends JeezObject  implements Comparable<Integer> {

    private final int value;
    
    public Integer(int value) {
      super(getIntegerClass());
      this.value = value;
    }
    
    public java.lang.String toString() {
      return java.lang.Integer.toString(value);
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Integer) {
        return value == ((Integer) obj).value;
      }
      if (obj instanceof java.lang.Integer) {
        return value == ((java.lang.Integer) obj).intValue();
      }
      
      return false;
    }
    
    @Override
    public int hashCode() {
      return value;
    }
    
    @Override
    public int compareTo(Integer anotherInteger) {
      return value < anotherInteger.value ? -1 : (value == anotherInteger.value ? 0 : 1);
    }
  }
}
