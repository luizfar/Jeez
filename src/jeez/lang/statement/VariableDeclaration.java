package jeez.lang.statement;

import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class VariableDeclaration implements Statement {

  private String variableName;
  
  public VariableDeclaration(String variableName) {
    this.variableName = variableName;
  }
  
  @Override
  public void execute(ExecutionContext context) {
    Variable variable = new Variable(variableName);
    context.addToLocalContext(variable);
  }
}
