package com.jeez.compiler.ast.type;

import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Type;

public class JeezClassType implements Type {

  private JeezClass clazz;
  
  public JeezClassType(JeezClass clazz) {
    this.clazz = clazz;
  }
  
  public JeezClass getClazz() {
    return clazz;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitJeezClassType(this);
  }
}
