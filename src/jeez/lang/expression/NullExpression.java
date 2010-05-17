package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class NullExpression implements Expression {

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return null;
  }
}
