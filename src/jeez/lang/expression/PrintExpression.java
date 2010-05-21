package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class PrintExpression implements Expression {

  private Expression expression;
  
  public PrintExpression(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    System.out.print(expression.evaluate(context));
    return null;
  }
}
