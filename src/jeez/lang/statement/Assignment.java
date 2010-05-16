package jeez.lang.statement;

import jeez.lang.Variable;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Assignment implements Statement {

  private String variableName;
  
  private Expression expression;
  
  public Assignment(String variableName, Expression expression) {
    this.variableName = variableName;
    this.expression = expression;
  }

  @Override
  public void execute(ExecutionContext context) {
    Variable variable = context.getFromLocalContext(variableName);
    if (variable == null) {
      throw new RuntimeException("Could not find variable " + variableName);
    }
    variable.setValue(expression.evaluate(context));
  }
}
