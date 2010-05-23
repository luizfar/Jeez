package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.execution.MethodInvoker;
import jeez.interpreter.load.ClassCreator;

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
  
  public Expression getReceiver() {
    return receiver;
  }

  public String getMessageName() {
    return messageName;
  }

  public List<Expression> getArguments() {
    return arguments;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    Object target = receiver.evaluate(context);
    Object[] evaluatedArguments = new Object[arguments.size()];
    for (int i = 0; i < evaluatedArguments.length; i++) {
      evaluatedArguments[i] = arguments.get(i).evaluate(context);
    }
    
    return MethodInvoker.invoke(target, messageName, evaluatedArguments);
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateMessageSend(this);
  }
}
