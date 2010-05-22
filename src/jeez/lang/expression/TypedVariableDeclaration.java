package jeez.lang.expression;

import jeez.lang.TypedVariable;
import jeez.lang.execution.ClassManager;
import jeez.lang.execution.ExecutionContext;

public class TypedVariableDeclaration extends VariableDeclaration {

  private String className;
  
  public TypedVariableDeclaration(String className, String variableName) {
    super(variableName);
    this.className = className;
  }
  
  @SuppressWarnings("unchecked")
  public Object evaluate(ExecutionContext context) {
    Class clazz = ClassManager.findClass(className);
    TypedVariable var = new TypedVariable(clazz, variableName);
    context.addToLocalContext(var);
    
    if (initExpression != null) {
      var.setValue(initExpression.evaluate(context));
    }
    
    return var.getValue();
  }
}
