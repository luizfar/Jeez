package jeez.lang;

import jeez.lang.execution.ExecutionContext;
import jeez.lang.expression.Expression;

public class ClassAttribute {
  
  private Variable variable;

  private Expression initialExpression;
  
  public ClassAttribute(String name) {
    variable = new Variable(name);
  }
  
  public void setInitialExpression(Expression initialExpression) {
    this.initialExpression = initialExpression;
  }
  
  public Variable getVariable() {
    return variable;
  }
  
  public void init(ExecutionContext context) {
    if (initialExpression != null) {
      variable.setValue(initialExpression.evaluate(context));
    }
  }
}
