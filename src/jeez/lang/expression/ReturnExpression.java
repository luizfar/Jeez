package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class ReturnExpression implements Expression {

  private Expression expression;
  
  public ReturnExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    return expression.evaluate(context);
  }
}
