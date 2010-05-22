package jeez.lang.expression;

import static java.lang.Boolean.TRUE;
import jeez.lang.execution.ExecutionContext;

public class IfExpression implements Expression {

  private Expression booleanExpression;
  
  private Expression ifExpression;
  
  private Expression elseExpression;
  
  public IfExpression(Expression booleanExpression) {
    this.booleanExpression = booleanExpression;
  }
  
  public void setIfExpression(Expression ifExpression) {
    this.ifExpression = ifExpression;
  }
  
  public void setElseExpression(Expression elseExpression) {
    this.elseExpression = elseExpression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    if (TRUE.equals(booleanExpression.evaluate(context))) {
      return ifExpression.evaluate(context);
    } else if (elseExpression != null) {
      return elseExpression.evaluate(context);
    }
    
    return null;
  }
}
