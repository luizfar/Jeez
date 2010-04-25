package com.jeez.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class MethodParameterList implements ASTNode {

  private List<MethodParameter> parameters = new ArrayList<MethodParameter>();
  
  public void addParameter(MethodParameter parameter) {
    parameters.add(parameter);
  }
  
  public List<MethodParameter> getParameters() {
    return parameters;
  }

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitMethodParameterList(this);
  }
}
