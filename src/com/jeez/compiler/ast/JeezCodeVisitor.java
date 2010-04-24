package com.jeez.compiler.ast;

import com.jeez.compiler.ast.modifier.AbstractModifier;
import com.jeez.compiler.ast.modifier.PublicModifier;
import com.jeez.compiler.ast.modifier.StaticModifier;
import com.jeez.compiler.ast.modifier.visibility.PackageModifier;
import com.jeez.compiler.ast.modifier.visibility.PrivateModifier;
import com.jeez.compiler.ast.modifier.visibility.ProtectedModifier;
import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;

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

  void visitStaticModifier(StaticModifier staticModifier);

  void visitAbstractModifier(AbstractModifier abstractModifier);
}
