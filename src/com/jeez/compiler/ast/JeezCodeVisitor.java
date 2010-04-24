package com.jeez.compiler.ast;

public interface JeezCodeVisitor {

  void visitSourceUnit(SourceUnit sourceUnit);
  
  void visitClass(JeezClass clazz);
  
  void visitMethod(Method method);
}
