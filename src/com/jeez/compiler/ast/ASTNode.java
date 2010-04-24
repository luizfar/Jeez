package com.jeez.compiler.ast;

public interface ASTNode {

  void accept(JeezCodeVisitor visitor);
}
