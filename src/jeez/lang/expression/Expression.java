package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public interface Expression {
  
  public JeezObject evaluate(ExecutionContext context);
}
