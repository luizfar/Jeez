package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class ExpressionList implements Expression {

  private List<Expression> expressions = new ArrayList<Expression>();
  
  public void addToExpressions(Expression expression) {
    expressions.add(expression);
  }
  
  public List<Expression> getExpressions() {
    return expressions;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    JeezObject result = null;
    for (Expression expression : expressions) {
      result = expression.evaluate(context);
    }
    return result;
  }
}
