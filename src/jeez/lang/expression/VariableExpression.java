package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class VariableExpression implements Expression {

  private String variableName;
  
  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }
  
  public String getVariableName() {
    return variableName;
  }

  @Override
  public JeezObject evaluate(ExecutionContext context) {
    Variable variable = context.getFromAnyContext(variableName);
    if (variable == null) {
      throw new RuntimeException("Could not find variable " + variableName);
    }
    return variable.getValue();
  }
}
