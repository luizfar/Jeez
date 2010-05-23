package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.execution.exception.UnknownMessageException;
import jeez.interpreter.execution.exception.WrongNumberOfArgumentsException;
import jeez.lang.JeezObject;
import jeez.lang.Method;

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
    Method method = target.getMethod(messageName);
    
    if (method == null) {
      method = target.getJeezClass().getMethod(messageName);
    }
    
    if (method == null) {
      throw new UnknownMessageException(messageName);
    }
    
    if (method.getParametersCount() != arguments.size()) {
      throw new WrongNumberOfArgumentsException(method, arguments.size());
    }
    
    return method.execute(target, arguments, context);
  }
}
