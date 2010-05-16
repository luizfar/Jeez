package jeez.lang.expression;

import jeez.lang.context.ExecutionContext;
import jeez.lang.java.JeezInteger;

public class IntegerExpression implements Expression {

  private JeezInteger value;
  
  public IntegerExpression(JeezInteger value) {
    this.value = value;
  }

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return value;
  }
}
