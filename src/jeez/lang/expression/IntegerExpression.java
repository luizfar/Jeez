package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;
import jeez.lang.Integer;

public class IntegerExpression implements Expression {

  private Integer value;
  
  public IntegerExpression(Integer value) {
    this.value = value;
  }
  
  public Integer getValue() {
    return value;
  }

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return value;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateInteger(this);
  }
}
