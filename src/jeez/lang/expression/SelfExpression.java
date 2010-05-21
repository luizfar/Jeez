package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class SelfExpression implements Expression {

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    return context.getSelfContext();
  }
}
