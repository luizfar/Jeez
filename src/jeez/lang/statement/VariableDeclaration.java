package jeez.lang.statement;

import jeez.lang.Clazz;
import jeez.lang.TypedVariable;
import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class VariableDeclaration implements Statement {

  private String typeName;
  
  private String variableName;
  
  public VariableDeclaration(String typeName, String variableName) {
    this.typeName = typeName;
    this.variableName = variableName;
  }
  
  @Override
  public void execute(ExecutionContext context) {
    Clazz clazz = context.getClass(typeName);
    if (clazz == null) {
      throw new RuntimeException("Could not find class '" + typeName + "'");
    }
    
    Variable variable = new TypedVariable(clazz, variableName);
    context.addToLocalContext(variable);
  }
}
