package com.jeez.compiler.ast.modifier;

import com.jeez.compiler.ast.ASTNode;

public interface ClassMemberModifier extends ASTNode {

  public static final ClassMemberModifier STATIC_MODIFIER = new StaticModifier();
  
  public static final ClassMemberModifier ABSTRACT_MODIFIER = new AbstractModifier();
}
