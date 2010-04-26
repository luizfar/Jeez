package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class BinaryExpression implements Expression {

  private BinaryOperator operator;
  
  private Expression leftSide;
  
  private Expression rightSide;
  
  public BinaryExpression(BinaryOperator operator, Expression leftSide, Expression rightSide) {
    this.operator = operator;
    this.leftSide = leftSide;
    this.rightSide = rightSide;
  }
  
  public BinaryOperator getOperator() {
    return operator;
  }

  public Expression getLeftSide() {
    return leftSide;
  }

  public Expression getRightSide() {
    return rightSide;
  }

  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
