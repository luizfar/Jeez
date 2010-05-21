package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;
import jeez.lang.java.JeezBoolean;

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
  public JeezObject evaluate(ExecutionContext context) {
    if (JeezBoolean.TRUE.equals(booleanExpression.evaluate(context))) {
      ifExpression.evaluate(context);
    } else if (elseExpression != null) {
      elseExpression.evaluate(context);
    }
    
    return null;
  }
}
