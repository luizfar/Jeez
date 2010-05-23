package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public interface Expression {
  
  public JeezObject evaluate(ExecutionContext context);
}
