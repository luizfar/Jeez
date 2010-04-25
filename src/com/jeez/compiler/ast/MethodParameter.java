package com.jeez.compiler.ast;

public class MethodParameter extends LocalVariable {

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitMethodParameter(this);
  }
}
