package jeez.lang.expression;

import jeez.lang.context.ExecutionContext;

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
}
