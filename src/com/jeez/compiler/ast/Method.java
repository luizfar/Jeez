package com.jeez.compiler.ast;

import static com.jeez.compiler.ast.modifier.ClassMemberModifier.*;
import static com.jeez.compiler.ast.modifier.visibility.VisibilityModifier.*;

import java.util.HashSet;
import java.util.Set;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;

public class Method extends ClassMember {
  
  private static final Set<ClassMemberModifier> ALLOWED_MODIFIERS = new HashSet<ClassMemberModifier>();

  static {
    ALLOWED_MODIFIERS.add(PUBLIC_MODIFIER);
    ALLOWED_MODIFIERS.add(PROTECTED_MODIFIER);
    ALLOWED_MODIFIERS.add(PRIVATE_MODIFIER);
    ALLOWED_MODIFIERS.add(PACKAGE_MODIFIER);
    
    ALLOWED_MODIFIERS.add(STATIC_MODIFIER);
    ALLOWED_MODIFIERS.add(ABSTRACT_MODIFIER);
  }
  
  private MethodParameterList parameters;
  
  public void setParameters(MethodParameterList parameters) {
    this.parameters = parameters;
  }

  public MethodParameterList getParameters() {
    return parameters;
  }

  @Override
  public Set<ClassMemberModifier> getAllowedModifiers() {
    return ALLOWED_MODIFIERS;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitMethod(this);
  }
}
