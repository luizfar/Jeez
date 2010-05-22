package jeez.lang.expression;

import jeez.lang.execution.ExecutionContext;
import jeez.lang.Integer;

public class IntegerExpression implements Expression {

  private Integer value;
  
  public IntegerExpression(Integer value) {
    this.value = value;
  }

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return value;
  }
}
