package jeez.lang.statement;

import jeez.lang.DynamicVariable;
import jeez.lang.context.ExecutionContext;

public class DynamicVariableDeclaration implements Statement {

  private String variableName;
  
  public DynamicVariableDeclaration(String variableName) {
    this.variableName = variableName;
  }
  
  @Override
  public void execute(ExecutionContext symbolTable) {
    symbolTable.addToLocalContext(new DynamicVariable(variableName));
  }
}
