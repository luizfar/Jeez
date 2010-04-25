package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class AndExpression implements Expression {

  private Expression left;
  
  private Expression right;
  
  public AndExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }
  
  public Expression getLeft() {
    return left;
  }
  
  public Expression getRight() {
    return right;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
