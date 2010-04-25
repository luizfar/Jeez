package com.jeez.compiler.ast;

public interface Variable extends ASTNode {

  String getName();

  public Type getType();
}
