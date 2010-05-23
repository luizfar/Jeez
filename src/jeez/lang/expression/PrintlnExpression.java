package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;

public class PrintlnExpression implements Expression {

  private Expression expression;
  
  public PrintlnExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    System.out.println(expression.evaluate(context));
    return null;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generatePrintln(this);
  }
}
