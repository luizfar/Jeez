package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;

public class SelfExpression implements Expression {

  @Override
  public Object evaluate(ExecutionContext context) {
    return context.getSelfContext();
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateSelf(this);
  }
}
