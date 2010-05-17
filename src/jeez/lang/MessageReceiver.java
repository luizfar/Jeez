package jeez.lang;

import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public interface MessageReceiver {

  public Object receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context);
}
