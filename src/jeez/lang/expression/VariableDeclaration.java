package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class VariableDeclaration implements Expression {

  private String variableName;
  
  public VariableDeclaration(String variableName) {
    this.variableName = variableName;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    Variable var = new Variable(variableName);
    context.addToLocalContext(var);
    return null;
  }
}
