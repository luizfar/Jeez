package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Variable implements MessageReceiver {

  private String name;
  
  private JeezObject value;
  
  public Variable(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setValue(JeezObject value) {
    this.value = value;
  }
  
  public JeezObject getValue() {
    return value;
  }

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    return getValue().receiveMessage(messageName, arguments, context);
  }
}
