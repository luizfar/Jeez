package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class PrintExpression implements Expression {

  private List<Expression> expressions = new ArrayList<Expression>();
  
  public void addToExpressions(Expression expression) {
    expressions.add(expression);
  }
  
  public List<Expression> getExpressions() {
    return expressions;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    for (Expression expression : expressions) {
      System.out.print(expression.evaluate(context));
    }
    
    return null;
  }
}
