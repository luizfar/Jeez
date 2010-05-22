package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public class ReturnExpression implements Expression {

  private Expression expression;
  
  public ReturnExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    return expression.evaluate(context);
  }
}
