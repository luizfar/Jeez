package com.jeez.compiler.ast;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;

public class InstanceVariable extends ClassMember {
  
  @Override
  public void addModifier(ClassMemberModifier modifier) {
    super.addModifier(modifier);
  }
  
  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitInstanceVariable(this);
  }  
}
