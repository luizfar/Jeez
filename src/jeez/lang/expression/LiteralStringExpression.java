package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;

public class LiteralStringExpression implements Expression {

  private String value;
  
  public LiteralStringExpression(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }

  @Override
  public Object evaluate(ExecutionContext symbolTable) {
    return value;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateString(this);
  }
}
