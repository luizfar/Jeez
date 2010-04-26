package com.jeez.compiler.ast.type;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Type;

public class VoidType implements Type {

  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitVoidType(this);
  }
}
