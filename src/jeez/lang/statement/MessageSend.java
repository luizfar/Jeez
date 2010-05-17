package jeez.lang.statement;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.JeezObject;
import jeez.lang.MessageReceiver;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class MessageSend implements Statement {

  private String receiverName;
  
  private String messageName;
  
  private List<Expression> arguments = new ArrayList<Expression>();
  
  private JeezObject result;
  
  public MessageSend(String receiverName, String messageName) {
    this.receiverName = receiverName;
    this.messageName = messageName;
  }
  
  public void addToArguments(Expression expression) {
    arguments.add(expression);
  }
  
  @Override
  public void execute(ExecutionContext context) {
    MessageReceiver receiver = context.getMessageReceiver(receiverName);
    result = receiver.receiveMessage(messageName, arguments, context);
  }
  
  public JeezObject getReturnedValue() {
    return result;
  }
}
