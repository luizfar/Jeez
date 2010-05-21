package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;

public class AssignmentExpression implements Expression {

  private String variableName;
  
  private Expression expression;
  
  public AssignmentExpression(String variableName, Expression expression) {
    this.variableName = variableName;
    this.expression = expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    Variable var = context.getFromAnyContext(variableName);
    var.setValue(expression.evaluate(context));
    
    return new VariableExpression(variableName).evaluate(context);
  }
}
