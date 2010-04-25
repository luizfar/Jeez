package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class LiteralBooleanExpression implements Expression {

  private boolean value;
  
  public LiteralBooleanExpression(boolean value) {
    this.value = value;
  }
  
  public boolean getValue() {
    return value;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
