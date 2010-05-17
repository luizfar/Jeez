package jeez.lang.expression;

import jeez.lang.JeezObject;
import jeez.lang.context.ExecutionContext;

import com.jeez.compiler.lexer.Symbol;

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
