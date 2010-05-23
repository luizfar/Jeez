package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;

public class ReturnExpression implements Expression {

  private Expression expression;
  
  public ReturnExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    return expression.evaluate(context);
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateReturn(this);
  }
}
