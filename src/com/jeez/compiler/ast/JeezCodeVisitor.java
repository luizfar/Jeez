package com.jeez.compiler.ast;

import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;
import com.jeez.compiler.ast.visibility.PackageModifier;
import com.jeez.compiler.ast.visibility.PrivateModifier;
import com.jeez.compiler.ast.visibility.ProtectedModifier;
import com.jeez.compiler.ast.visibility.PublicModifier;

public interface JeezCodeVisitor {

  void visitSourceUnit(JeezSource sourceUnit);
  
  void visitClass(JeezClass clazz);

  void visitInstanceVariable(InstanceVariable attribute);
  
  void visitMethod(Method method);

  void visitMethodParameter(MethodParameter methodParameter);

  void visitInteger(IntegerType integerType);

  void visitBoolean(BooleanType booleanType);

  void visitVoid(VoidType voidType);

  void visitVariable(Variable variable);

  void visitMethodParameterList(MethodParameterList methodParameterList);

  void visitPublicModifier(PublicModifier publicModifier);

  void visitProtectedModifier(ProtectedModifier protectedModifier);

  void visitPrivateModifier(PrivateModifier privateModifier);

  void visitPackageModifier(PackageModifier packageModifier);
}
