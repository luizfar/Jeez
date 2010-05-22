package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public class PrintExpression implements Expression {

  private Expression expression;
  
  public PrintExpression(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    System.out.print(expression.evaluate(context));
    return null;
  }
}
