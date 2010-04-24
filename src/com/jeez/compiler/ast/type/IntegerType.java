package com.jeez.compiler.ast.type;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Type;

public class IntegerType implements Type {

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitInteger(this);
  }
}
