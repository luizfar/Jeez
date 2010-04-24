package com.jeez.compiler.ast.modifier;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class AbstractModifier implements ClassMemberModifier {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitAbstractModifier(this);
  }
}
