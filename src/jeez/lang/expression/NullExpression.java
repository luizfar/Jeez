package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public class NullExpression implements Expression {

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return null;
  }
}