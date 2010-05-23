package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class PrintlnExpression implements Expression {

  private Expression expression;
  
  public PrintlnExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    System.out.println(expression.evaluate(context));
    return null;
  }
}
