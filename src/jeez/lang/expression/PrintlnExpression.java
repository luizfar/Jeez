package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class PrintlnExpression implements Expression {

  private Expression expression;
  
  public PrintlnExpression(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    System.out.println(expression.evaluate(context));
    return null;
  }
}
