package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

public interface Expression {
  
  public Object evaluate(ExecutionContext context);

  public void accept(ClassCreator classCreator);
}
