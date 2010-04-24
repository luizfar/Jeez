package com.jeez.compiler.ast;

public class MethodParameter extends Variable {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitMethodParameter(this);
  }
}
