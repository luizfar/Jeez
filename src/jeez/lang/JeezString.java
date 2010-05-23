package jeez.lang;

import static jeez.lang.Constants.NEW;
import jeez.lang.expression.ReturnExpression;
import jeez.lang.expression.VariableExpression;

public class JeezString extends JeezClass {

  public static JeezString STRING = new JeezString();
  
  public JeezString() {
    super("String");
    
    Method newString = new Method(this, this, NEW);
    newString.addToParameters(new TypedVariable(this, "str"));
    newString.getBlock().addToExpressions(new ReturnExpression(new VariableExpression("str")));
    
    addToMethods(newString);
  }
  
  public static JeezStringObject newValue(String value) {
    return new JeezStringObject(value);
  }
  
  static class JeezStringObject extends JeezObject {
    String value;    
    public JeezStringObject(String value) {
      this.clazz = STRING;
      this.value = value;
    }
    @Override
    public String toString() {
      return value;
    }
    @Override
    public int hashCode() {
      return value.hashCode();
    }
  }
}
