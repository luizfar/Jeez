package com.jeez.compiler.ast;

public abstract class Variable implements ASTNode {

  private Type type;
  
  private String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }
}
