package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

public class PrintExpression implements Expression {

  private Expression expression;
  
  public PrintExpression(Expression expression) {
    this.expression = expression;
  }
  
  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    System.out.print(expression.evaluate(context));
    return null;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generatePrint(this);
  }
}
