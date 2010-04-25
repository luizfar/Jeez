package com.jeez.compiler.ast;

public interface ASTNode {

  void receive(JeezCodeVisitor visitor);
}
