package jeez.lang;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.expression.Expression;

public class Block {

  private List<Expression> expressions = new ArrayList<Expression>();
  
  public void addToExpressions(Expression expression) {
    expressions.add(expression);
  }
  
  public List<Expression> getExpressions() {
    return expressions;
  }
}
