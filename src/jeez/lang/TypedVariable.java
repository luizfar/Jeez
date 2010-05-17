package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class TypedVariable extends Variable {

  private Clazz type;
  
  public TypedVariable(Clazz type, String name) {
    super(name);
  }
  
  public Clazz getType() {
    return type;
  }

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    Method method = type.getMethod(messageName);
    return method.execute(getValue(), arguments, context);
  }
}
