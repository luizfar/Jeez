package com.jeez.compiler.ast;

public class InstanceVariable extends Variable implements ClassMember {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitInstanceVariable(this);
  }
}
