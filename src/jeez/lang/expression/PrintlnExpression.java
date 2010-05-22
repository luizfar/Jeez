package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public class PrintlnExpression implements Expression {

  private Expression expression;
  
  public PrintlnExpression(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    System.out.println(expression.evaluate(context));
    return null;
  }
}
