package com.jeez.compiler.ast.modifier.visibility;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class PrivateModifier implements VisibilityModifier {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitPrivateModifier(this);
  }

}
