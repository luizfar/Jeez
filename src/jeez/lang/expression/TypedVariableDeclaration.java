package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.execution.exception.ClassNotFoundException;
import jeez.lang.JeezClass;
import jeez.lang.JeezObject;
import jeez.lang.TypedVariable;

public class TypedVariableDeclaration extends VariableDeclaration {

  private String className;
  
  public TypedVariableDeclaration(String className, String variableName) {
    super(variableName);
    this.className = className;
  }
  
  public JeezObject evaluate(ExecutionContext context) {
    JeezClass clazz = context.getClass(className);
    if (clazz == null) {
      throw new ClassNotFoundException(className.toString());
    }
    
    TypedVariable var = new TypedVariable(clazz, variableName);
    context.addToLocalContext(var);
    
    if (initExpression != null) {
      var.setValue(initExpression.evaluate(context));
    }
    
    return var.getValue();
  }
}
