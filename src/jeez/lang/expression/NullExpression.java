package jeez.lang.expression;

import jeez.lang.context.ExecutionContext;

public class NullExpression implements Expression {

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return null;
  }
}
