package com.jeez.compiler.ast;

public class Method implements ClassMember {

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

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitMethod(this);
  }
}
