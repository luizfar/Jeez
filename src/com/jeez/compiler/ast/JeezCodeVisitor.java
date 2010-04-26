package com.jeez.compiler.ast;

import com.jeez.compiler.ast.expr.BinaryExpression;
import com.jeez.compiler.ast.expr.BinaryOperator;
import com.jeez.compiler.ast.expr.IntegerExpression;
import com.jeez.compiler.ast.expr.LiteralStringExpression;
import com.jeez.compiler.ast.expr.VariableExpression;
import com.jeez.compiler.ast.modifier.AbstractModifier;
import com.jeez.compiler.ast.modifier.PublicModifier;
import com.jeez.compiler.ast.modifier.StaticModifier;
import com.jeez.compiler.ast.modifier.visibility.PackageModifier;
import com.jeez.compiler.ast.modifier.visibility.PrivateModifier;
import com.jeez.compiler.ast.modifier.visibility.ProtectedModifier;
import com.jeez.compiler.ast.stmt.CompositeStatement;
import com.jeez.compiler.ast.stmt.IfStatement;
import com.jeez.compiler.ast.stmt.PrintStatement;
import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;

public interface JeezCodeVisitor {

  void visitSourceUnit(JeezSource sourceUnit);
  
  void visitClass(JeezClass clazz);

  void visitInstanceVariable(InstanceVariable attribute);
  
  void visitMethod(Method method);

  void visitMethodParameter(MethodParameter methodParameter);

  void visitIntegerType(IntegerType integerType);

  void visitBooleanType(BooleanType booleanType);

  void visitVoidType(VoidType voidType);

  void visitVariable(Variable variable);

  void visitMethodParameterList(MethodParameterList methodParameterList);

  void visitPublicModifier(PublicModifier publicModifier);

  void visitProtectedModifier(ProtectedModifier protectedModifier);

  void visitPrivateModifier(PrivateModifier privateModifier);

  void visitPackageModifier(PackageModifier packageModifier);

  void visitStaticModifier(StaticModifier staticModifier);

  void visitAbstractModifier(AbstractModifier abstractModifier);

  void visitPrintStatement(PrintStatement printStatement);

  void visitLiteralStringExpr(LiteralStringExpression literalStringExpression);

  void visitIfStatement(IfStatement ifStatement);

  void visitCompositeStatement(CompositeStatement compositeStatement);

  void visitBinaryExpression(BinaryExpression binaryExpression);

  void visitVariableExpression(VariableExpression variableExpression);

  void visitIntegerExpression(IntegerExpression integerExpression);

  void visitBinaryOperator(BinaryOperator binaryOperator);
}
