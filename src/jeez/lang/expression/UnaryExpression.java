package jeez.lang.expression;

import jeez.interpreter.lexer.Symbol;
import jeez.interpreter.load.ClassCreator;
import jeez.lang.execution.ExecutionContext;

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
  public Object evaluate(ExecutionContext symbolTable) {
    return null;
  }

  @Override
  public void accept(ClassCreator classCreator) {
    classCreator.generateUnaryExpression(this);
  }
}
