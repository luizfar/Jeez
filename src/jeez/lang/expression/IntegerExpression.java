package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;
import jeez.lang.JeezInteger.Integer;

public class IntegerExpression implements Expression {

  private Integer value;
  
  public IntegerExpression(Integer value) {
    this.value = value;
  }
  
  public Integer getValue() {
    return value;
  }

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return value;
  }
}
