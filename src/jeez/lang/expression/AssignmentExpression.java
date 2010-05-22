package jeez.lang.expression;

import jeez.interpreter.load.ClassCreator;
import jeez.lang.Variable;
import jeez.lang.execution.ExecutionContext;

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
  public Object evaluate(ExecutionContext context) {
    Variable var = context.getFromAnyContext(variableName);
    var.setValue(expression.evaluate(context));
    
    return new VariableExpression(variableName).evaluate(context);
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateAssignment(this);
  }
}
