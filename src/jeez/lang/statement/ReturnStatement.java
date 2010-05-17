package jeez.lang.statement;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class ReturnStatement implements Statement {

  private Expression expression;
  
  private JeezObject result;
  
  public ReturnStatement(Expression expression) {
    this.expression = expression;
  }
  
  @Override
  public void execute(ExecutionContext context) {
    result = expression.evaluate(context);
  }
  
  public JeezObject getResult() {
    return result;
  }
}
