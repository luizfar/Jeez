package com.jeez.compiler.ast;

public class MethodParameter extends Variable {

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitMethodParameter(this);
  }
}
