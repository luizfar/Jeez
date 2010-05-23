package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class PrintExpression implements Expression {

  private Expression expression;
  
  public PrintExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    System.out.print(expression.evaluate(context));
    return null;
  }
}
