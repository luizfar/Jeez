package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.load.ClassCreator;
import jeez.lang.Variable;

public class VariableDeclaration implements Expression {

  protected String variableName;
  
  protected Expression initExpression;
  
  public VariableDeclaration(String variableName) {
    this.variableName = variableName;
  }
  
  public void setInitExpression(Expression initExpression) {
    this.initExpression = initExpression;
  }
  
  @Override
  public Object evaluate(ExecutionContext context) {
    Variable var = new Variable(variableName);
    context.addToLocalContext(var);
    
    if (initExpression != null) {
      var.setValue(initExpression.evaluate(context));
    }
    
    return var.getValue();
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateVariableDeclaration(this);
  }
}
