package com.jeez.compiler.ast;

public class LocalVariable implements Variable {

  private Type type;
  
  private String name;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }  
}