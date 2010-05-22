package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;

public interface Expression {
  
  public Object evaluate(ExecutionContext context);
}
