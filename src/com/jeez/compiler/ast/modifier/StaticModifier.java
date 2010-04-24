package com.jeez.compiler.ast.modifier;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class StaticModifier implements ClassMemberModifier {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitStaticModifier(this);
  }
}
