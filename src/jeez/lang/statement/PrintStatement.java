package jeez.lang.statement;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class PrintStatement implements Statement {

  private Expression expression;
  
  public PrintStatement(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public void execute(ExecutionContext symbolTable) {
    System.out.print(expression.evaluate(symbolTable));
  }
}
