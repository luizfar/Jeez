package jeez.lang.statement;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class ReturnStatement implements Statement {

  private Expression expression;
  
  private Object result;
  
  public ReturnStatement(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public void execute(ExecutionContext context) {
    result = expression.evaluate(context);
  }
  
  public Object getResult() {
    return result;
  }
}
