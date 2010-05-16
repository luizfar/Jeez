package jeez.lang.expression;

import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class VariableExpression implements Expression {

  private String variableName;
  
  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    Variable variable = context.getFromLocalContext(variableName);
    if (variable == null) {
      throw new RuntimeException("Could not find variable " + variableName);
    }
    return variable.getValue();
  }
}
