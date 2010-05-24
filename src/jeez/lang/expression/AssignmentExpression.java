package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.execution.exception.UnknownVariableException;
import jeez.lang.JeezObject;
import jeez.lang.Variable;

public class AssignmentExpression implements Expression {

  private String variableName;
  
  private Expression expression;
  
  public AssignmentExpression(String variableName, Expression expression) {
    this.variableName = variableName;
    this.expression = expression;
  }
  
  public String getVariableName() {
    return variableName;
  }
  
  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public JeezObject evaluate(ExecutionContext context) {
    Variable var = context.getFromAnyContext(variableName);
    if (var == null) {
      throw new UnknownVariableException(variableName);
    }
    var.setValue(expression.evaluate(context));
    
    return new VariableExpression(variableName).evaluate(context);
  }
}
