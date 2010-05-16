package jeez.lang.expression;

import jeez.lang.context.ExecutionContext;

public interface Expression {
  
  public Object evaluate(ExecutionContext context);
}
