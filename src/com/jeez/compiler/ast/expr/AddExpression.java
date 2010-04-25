package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.lexer.Symbol;

public class AddExpression implements Expression {
  
  private Symbol operator;

  private Expression left;
  
  private Expression right;
  
  public AddExpression(Expression left, Expression right, Symbol operator) {
    this.left = left;
    this.right = right;
    this.operator = operator;
  }
  
  public Expression getLeft() {
    return left;
  }
  
  public Expression getRight() {
    return right;
  }
  
  public Symbol getOperator() {
    return operator;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
