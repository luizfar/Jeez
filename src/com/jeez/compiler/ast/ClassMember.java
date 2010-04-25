package com.jeez.compiler.ast;

import java.util.HashSet; 
import java.util.Set;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;

public abstract class ClassMember implements ASTNode {

  private Set<ClassMemberModifier> modifiers = new HashSet<ClassMemberModifier>();
  
  private Type type;
  
  private String name;
  
  abstract public Set<ClassMemberModifier> getAllowedModifiers();
  
  public boolean accepts(ClassMemberModifier modifier) {
    return getAllowedModifiers().contains(modifier);
  }
  
  public void addModifier(ClassMemberModifier modifier) {
    modifiers.add(modifier);
  }
  
  public Set<ClassMemberModifier> getModifiers() {
    return modifiers;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
