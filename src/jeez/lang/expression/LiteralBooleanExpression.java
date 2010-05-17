package jeez.lang.expression;

import static jeez.lang.java.JeezBoolean.FALSE;
import static jeez.lang.java.JeezBoolean.TRUE;
import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

public class LiteralBooleanExpression implements Expression {

  private boolean value;
  
  public LiteralBooleanExpression(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return value ? TRUE : FALSE;
  }
}
