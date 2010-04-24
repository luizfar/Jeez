package com.jeez.compiler.ast.modifier.visibility;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class PackageModifier implements VisibilityModifier {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitPackageModifier(this);
  }

}
