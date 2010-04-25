package com.jeez.compiler.ast;

import static com.jeez.compiler.ast.modifier.visibility.VisibilityModifier.*;

import java.util.HashSet;
import java.util.Set;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.ast.modifier.visibility.VisibilityModifier;

public abstract class ClassMember implements ASTNode {
  
  private Set<ClassMemberModifier> modifiers = new HashSet<ClassMemberModifier>();
  
  private Type type;
  
  private String name;
  
  private boolean hasVisibilityModifier;
  
  private boolean isStatic;
  
  private boolean isAbstract;
  
  abstract public Set<ClassMemberModifier> getAllowedModifiers();
  
  public boolean accepts(ClassMemberModifier modifier) {
    if (!getAllowedModifiers().contains(modifier)) {
      return false;
    }
    
    if (modifier instanceof VisibilityModifier && hasVisibilityModifier) {
      return false;
    }
    
    if (modifier == STATIC_MODIFIER && isAbstract) {
      return false;
    }
    
    if (modifier == ABSTRACT_MODIFIER && isStatic) {
      return false;
    }
    
    return true;
  }
  
  public void addModifier(ClassMemberModifier modifier) {
    if (modifier instanceof VisibilityModifier) {
      hasVisibilityModifier = true;
    }
    if (modifier == STATIC_MODIFIER) {
      isStatic = true;
    }
    if (modifier == ABSTRACT_MODIFIER) {
      isAbstract = true;
    }
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
