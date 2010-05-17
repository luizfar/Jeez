package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;
import jeez.lang.statement.MessageSend;

public class MessageSendExpression implements Expression {

  private MessageSend messageSend;
  
  public MessageSendExpression(MessageSend messageSend) {
    this.messageSend = messageSend;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    messageSend.execute(context);
    return messageSend.getReturnedValue();
  }
}
