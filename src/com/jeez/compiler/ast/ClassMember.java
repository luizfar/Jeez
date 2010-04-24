package com.jeez.compiler.ast;

public interface ClassMember extends ASTNode {

  VisibilityModifier getVisibilityModifier();
  
  boolean isStatic();
}
