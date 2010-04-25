package com.jeez.compiler.ast.modifier;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.modifier.visibility.VisibilityModifier;

public class PublicModifier implements VisibilityModifier {

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitPublicModifier(this);
  }

}
