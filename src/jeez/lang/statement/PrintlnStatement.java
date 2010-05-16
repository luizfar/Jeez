package jeez.lang.statement;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class PrintlnStatement implements Statement {

  private Expression expression;
  
  public PrintlnStatement(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public void execute(ExecutionContext symbolTable) {
    System.out.println(expression.evaluate(symbolTable));
  }
}
