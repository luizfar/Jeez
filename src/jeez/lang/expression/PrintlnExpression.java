package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class PrintlnExpression extends PrintExpression {

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    for (Expression expression : getExpressions()) {
      System.out.println(expression.evaluate(context));
    }
    
    return null;
  }
}
