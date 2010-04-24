package com.jeez.compiler.ast;

public class Method implements ClassMember {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitMethod(this);
  }
}
