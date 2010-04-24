package com.jeez.compiler.ast;

public class Method implements ClassMember {
  
  private VisibilityModifier visibilityModifier;
  
  private boolean isStatic;

  private Type returnType;
  
  private String name;
  
  private MethodParameterList parameters;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

  public Type getReturnType() {
    return returnType;
  }

  public void setParameters(MethodParameterList parameters) {
    this.parameters = parameters;
  }

  public MethodParameterList getParameters() {
    return parameters;
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

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitMethod(this);
  }
}
