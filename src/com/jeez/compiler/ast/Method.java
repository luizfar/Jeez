package com.jeez.compiler.ast;

import static com.jeez.compiler.ast.modifier.ClassMemberModifier.*;
import static com.jeez.compiler.ast.modifier.visibility.VisibilityModifier.*;

import java.util.HashSet;
import java.util.Set;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.ast.stmt.StatementList;

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
  
  private int bodyLocation;
  
  private MethodParameterList parameterList;
  
  private StatementList statementList;
  
  public void setParameterList(MethodParameterList parameterList) {
    this.parameterList = parameterList;
  }

  public MethodParameterList getParameterList() {
    return parameterList;
  }

  @Override
  public Set<ClassMemberModifier> getAllowedModifiers() {
    return ALLOWED_MODIFIERS;
  }
  
  public int getBodyLocation() {
    return bodyLocation;
  }

  public void setBodyLocation(int bodyLocation) {
    this.bodyLocation = bodyLocation;
  }

  public StatementList getStatementList() {
    return statementList;
  }

  public void setStatementList(StatementList statementList) {
    this.statementList = statementList;
  }

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitMethod(this);
  }
}
