package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.JeezObject;
import jeez.lang.JeezString;

public class LiteralStringExpression implements Expression {

  private String value;
  
  public LiteralStringExpression(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return JeezString.newValue(value);
  }
}
