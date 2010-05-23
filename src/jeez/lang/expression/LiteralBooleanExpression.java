package jeez.lang.expression;

import static jeez.lang.JeezBoolean.FALSE;
import static jeez.lang.JeezBoolean.TRUE;
import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;

public class LiteralBooleanExpression implements Expression {

  private boolean value;
  
  public LiteralBooleanExpression(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {;
    return value ? TRUE : FALSE;
  }
}
