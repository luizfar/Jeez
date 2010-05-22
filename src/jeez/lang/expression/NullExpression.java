package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

public class NullExpression implements Expression {

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return null;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateNull(this);
  }
}