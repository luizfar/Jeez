package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;

public interface Expression {
  
  public Object evaluate(ExecutionContext context);

  public void accept(ClassCreator classCreator);
}
