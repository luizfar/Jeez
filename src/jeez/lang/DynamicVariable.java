package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class DynamicVariable extends Variable {

  private Clazz type;
  
  public DynamicVariable(String name) {
    super(name);
  }
  
  public void setValue(JeezObject value) {
    super.setValue(value);
    type = ((JeezObject) value).getClazz();
  }

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    Method method = type.getMethod(messageName);
    return method.execute(getValue(), arguments, context);
  }
}