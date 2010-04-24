package com.jeez.compiler.ast;

public class Method extends ClassMember {
  
  private MethodParameterList parameters;
  
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
