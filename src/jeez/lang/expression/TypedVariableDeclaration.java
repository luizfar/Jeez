package jeez.lang.expression;

import jeez.lang.Clazz;
import jeez.lang.JeezObject;
import jeez.lang.TypedVariable;
import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class TypedVariableDeclaration implements Expression {

  private String className;
  
  private String variableName;
  
  public TypedVariableDeclaration(String className, String variableName) {
    this.className = className;
    this.variableName = variableName;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    Clazz clazz = context.getClass(className);
    if (clazz == null) {
      throw new RuntimeException("Can't find class with name '" + className + "'");
    }
    Variable typedVariable = new TypedVariable(clazz, variableName);
    context.addToLocalContext(typedVariable);
    
    return null;
  }
}
