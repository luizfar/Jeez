package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class IntegerExpression implements Expression {

  private int value;
  
  public IntegerExpression(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitIntegerExpression(this);
  }
}
