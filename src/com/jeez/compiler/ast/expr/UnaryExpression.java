package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;
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
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
