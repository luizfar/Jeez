package jeez.lang.expression;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

public class ExpressionList implements Expression {

  private List<Expression> expressions = new ArrayList<Expression>();
  
  public void addToExpressions(Expression expression) {
    expressions.add(expression);
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    Object result = null;
    for (Expression expression : expressions) {
      result = expression.evaluate(context);
    }
    return result;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateExpressionList(this);
  }
}
