package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezCodeVisitor;

public class InstantiationExpression implements Expression {

  private JeezClass clazz;
  
  public InstantiationExpression(JeezClass clazz) {
    this.clazz = clazz;
  }
  
  public JeezClass getJeezClass() {
    return clazz;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
