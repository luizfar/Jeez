package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Variable;

public class VariableExpression implements Expression {

  private Variable variable;
  
  public VariableExpression(Variable variable) {
    this.variable = variable;
  }
  
  public Variable getVariable() {
    return variable;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
