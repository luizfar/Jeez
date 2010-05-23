package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class NullExpression implements Expression {

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return null;
  }
}