package com.jeez.compiler.ast.type;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Type;

@SuppressWarnings("unchecked")
public class JavaClassType implements Type {

  private Class clazz;
  
  public JavaClassType(Class clazz) {
    this.clazz = clazz;
  }
  
  public Class getClazz() {
    return clazz;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
