package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

public class LiteralBooleanExpression implements Expression {

  private boolean value;
  
  public LiteralBooleanExpression(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return value ? Boolean.TRUE : Boolean.FALSE;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateBoolean(this);
  }
}
