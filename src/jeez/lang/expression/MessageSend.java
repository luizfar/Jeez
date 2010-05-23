package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.execution.MethodInvoker;
import jeez.lang.JeezObject;

public class MessageSend implements Expression {

  private Expression receiver;
  
  private String messageName;
  
  private List<Expression> arguments = new ArrayList<Expression>();
  
  public MessageSend(Expression receiver, String messageName) {
    this.receiver = receiver;
    this.messageName = messageName;
  }
  
  public void addToArguments(Expression expression) {
    arguments.add(expression);
  }

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    JeezObject target = receiver.evaluate(context);
    return new MethodInvoker().invoke(messageName, target, arguments, context);
  }
}
