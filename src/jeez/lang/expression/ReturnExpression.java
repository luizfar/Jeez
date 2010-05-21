package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class ReturnExpression implements Expression {

  private Expression expression;
  
  public ReturnExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    return expression.evaluate(context);
  }
}
