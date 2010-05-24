package jeez.lang.expression;

import static jeez.lang.JeezBoolean.TRUE;
import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

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
  
  public Expression getBooleanExpression() {
    return booleanExpression;
  }

  public Expression getIfExpression() {
    return ifExpression;
  }

  public Expression getElseExpression() {
    return elseExpression;
  }

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    if (TRUE.equals(booleanExpression.evaluate(context))) {
      return ifExpression.evaluate(context);
    } else if (elseExpression != null) {
      return elseExpression.evaluate(context);
    }
    
    return null;
  }
}
