package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public class SelfExpression implements Expression {

  @Override
  public Object evaluate(ExecutionContext context) {
    return context.getSelfContext();
  }
}
