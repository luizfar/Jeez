package jeez.lang.expression;

import jeez.interpreter.execution.ExecutionContext;
import jeez.interpreter.lexer.Symbol;
import jeez.lang.JeezObject;

public class UnaryExpression implements Expression {

  private Symbol operator;
  
  private Expression expression;
  
  public UnaryExpression(Symbol operator, Expression expression) {
    this.operator = operator;
    this.expression = expression;
  }
  
  public Symbol getOperator() {
    return operator;
  }
  
  public Expression getExpression() {
    return expression;
  }

  @Override
  public JeezObject evaluate(ExecutionContext symbolTable) {
    return null;
  }
}
