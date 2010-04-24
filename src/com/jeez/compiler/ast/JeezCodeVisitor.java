package com.jeez.compiler.ast;

import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;

public interface JeezCodeVisitor {

  void visitSourceUnit(SourceUnit sourceUnit);
  
  void visitClass(JeezClass clazz);

  void visitInstanceVariable(InstanceVariable attribute);
  
  void visitMethod(Method method);

  void visitMethodParameter(MethodParameter methodParameter);

  void visitInteger(IntegerType integerType);

  void visitBoolean(BooleanType booleanType);

  void visitVoid(VoidType voidType);

  void visitVariable(Variable variable);

  void visitMethodParameterList(MethodParameterList methodParameterList);
}
