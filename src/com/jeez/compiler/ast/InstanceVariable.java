package com.jeez.compiler.ast;

public class InstanceVariable extends Variable implements ClassMember {

  private VisibilityModifier visibilityModifier;
  
  private boolean isStatic;
  
  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitInstanceVariable(this);
  }

  public void setVisibilityModifier(VisibilityModifier visibilityModifier) {
    this.visibilityModifier = visibilityModifier;
  }

  @Override
  public VisibilityModifier getVisibilityModifier() {
    return visibilityModifier;
  }

  public void setStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  @Override
  public boolean isStatic() {
    return isStatic;
  }
}
