package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class MessageSendExpression implements Expression {

  private Expression receiver;
  
  private String messageName;
  
  private List<Expression> arguments = new ArrayList<Expression>();
  
  public MessageSendExpression(Expression receiver, String messageName) {
    this.receiver = receiver;
    this.messageName = messageName;
  }
  
  public void addToArguments(Expression expression) {
    arguments.add(expression);
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    JeezObject object = receiver.evaluate(context);
    return object.receiveMessage(messageName, arguments, context);
  }
}
