package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class SelfExpression implements Expression {

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    return context.getSelfContext();
  }
}
