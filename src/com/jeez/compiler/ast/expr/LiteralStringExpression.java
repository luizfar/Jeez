package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class LiteralStringExpression implements Expression {

  private String value;
  
  public LiteralStringExpression(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitLiteralStringExpr(this);
  }
}
