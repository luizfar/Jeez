package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;
import jeez.lang.Variable;

public class VariableExpression implements Expression {

  private String variableName;
  
  public VariableExpression(String variableName) {
    this.variableName = variableName;
  }
  
  public String getVariableName() {
    return variableName;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    Variable variable = context.getFromAnyContext(variableName);
    if (variable == null) {
      throw new RuntimeException("Could not find variable " + variableName);
    }
    return variable.getValue();
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateVariableExpression(this);
  }
}
