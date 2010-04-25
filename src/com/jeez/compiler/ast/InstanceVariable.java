package com.jeez.compiler.ast;

import static com.jeez.compiler.ast.modifier.ClassMemberModifier.*;
import static com.jeez.compiler.ast.modifier.visibility.VisibilityModifier.*;

import java.util.HashSet;
import java.util.Set;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;

public class InstanceVariable extends ClassMember {
  
  private static final Set<ClassMemberModifier> ALLOWED_MODIFIERS = new HashSet<ClassMemberModifier>();

  static {
    ALLOWED_MODIFIERS.add(PUBLIC_MODIFIER);
    ALLOWED_MODIFIERS.add(PROTECTED_MODIFIER);
    ALLOWED_MODIFIERS.add(PRIVATE_MODIFIER);
    ALLOWED_MODIFIERS.add(PACKAGE_MODIFIER);
    
    ALLOWED_MODIFIERS.add(STATIC_MODIFIER);
  }
  
  @Override
  public void addModifier(ClassMemberModifier modifier) {
    super.addModifier(modifier);
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitInstanceVariable(this);
  }

  @Override
  public Set<ClassMemberModifier> getAllowedModifiers() {
    return ALLOWED_MODIFIERS;
  }
}
