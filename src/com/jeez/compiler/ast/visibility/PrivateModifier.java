package com.jeez.compiler.ast.visibility;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.VisibilityModifier;

public class PrivateModifier implements VisibilityModifier {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitPrivateModifier(this);
  }

}
