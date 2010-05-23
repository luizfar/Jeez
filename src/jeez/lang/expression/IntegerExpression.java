package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.Integer;
import jeez.lang.JeezObject;

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
