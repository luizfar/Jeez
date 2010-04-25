package com.jeez.compiler.output;

import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.InstanceVariable;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
import com.jeez.compiler.ast.MethodParameterList;
import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.ast.JeezSourceMember;
import com.jeez.compiler.ast.Variable;
import com.jeez.compiler.ast.expr.Expression;
import com.jeez.compiler.ast.expr.LiteralStringExpression;
import com.jeez.compiler.ast.modifier.AbstractModifier;
import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.ast.modifier.PublicModifier;
import com.jeez.compiler.ast.modifier.StaticModifier;
import com.jeez.compiler.ast.modifier.visibility.PackageModifier;
import com.jeez.compiler.ast.modifier.visibility.PrivateModifier;
import com.jeez.compiler.ast.modifier.visibility.ProtectedModifier;
import com.jeez.compiler.ast.stmt.PrintStatement;
import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;

public class JavaGeneratorVisitor implements JeezCodeVisitor {

  private JeezPrintWriter printWriter;
  
  public JavaGeneratorVisitor(JeezPrintWriter printWriter) {
    this.printWriter = printWriter;
  }

  @Override
  public void visitSourceUnit(JeezSource sourceUnit) {
    for (JeezSourceMember member : sourceUnit.getMembers()) {
      member.receive(this);
    }
  }
  
  @Override
  public void visitClass(JeezClass clazz) {
    printWriter.println("public class " + clazz.getName() + " {");
    printWriter.add();
    
    for (ClassMember member : clazz.getMembers()) {
      member.receive(this);
    }
    
    printWriter.sub();
    printWriter.println("}");
  }

  @Override
  public void visitMethod(Method method) {
    printWriter.print("");
    for (ClassMemberModifier modifier : method.getModifiers()) {
      modifier.receive(this);
    }
    
    method.getType().receive(this);
    printWriter.append(" " + method.getName() + " ");
    method.getParameters().receive(this);
    printWriter.appendln(" {");
    printWriter.add();
    
    method.getStatementList().receive(this);
    
    printWriter.sub();
    printWriter.println("");
    printWriter.println("}");
  }
  
  @Override
  public void visitInstanceVariable(InstanceVariable variable) {
    printWriter.print("");
    for (ClassMemberModifier modifier : variable.getModifiers()) {
      modifier.receive(this);
    }
    
    variable.getType().receive(this);
    printWriter.appendln(" " + variable.getName() + ";");
  }

  @Override
  public void visitMethodParameter(MethodParameter parameter) {
    parameter.getType().receive(this);
    printWriter.append(" " + parameter.getName());
  }
  
  @Override
  public void visitVariable(Variable variable) {
    variable.getType().receive(this);
    printWriter.appendln(" " + variable.getName() + ";");
  }

  @Override
  public void visitBoolean(BooleanType booleanType) {
    printWriter.append("boolean");
  }

  @Override
  public void visitInteger(IntegerType integerType) {
    printWriter.append("int");
  }
  
  @Override
  public void visitVoid(VoidType voidType) {
    printWriter.append("void");
  }

  @Override
  public void visitMethodParameterList(MethodParameterList methodParameterList) {
    printWriter.append("(");
    for (int i = 0; i < methodParameterList.getParameters().size(); i++) {
      MethodParameter parameter = methodParameterList.getParameters().get(i);
      parameter.receive(this);
      if (i != methodParameterList.getParameters().size() - 1) {
        printWriter.append(", ");
      }
    }
    printWriter.append(")");
  }

  @Override
  public void visitPackageModifier(PackageModifier packageModifier) {
    printWriter.append("");
  }

  @Override
  public void visitPrivateModifier(PrivateModifier privateModifier) {
    printWriter.append("private ");
  }

  @Override
  public void visitProtectedModifier(ProtectedModifier protectedModifier) {
    printWriter.append("protected ");
  }

  @Override
  public void visitPublicModifier(PublicModifier publicModifier) {
    printWriter.append("public ");
  }

  @Override
  public void visitStaticModifier(StaticModifier staticModifier) {
    printWriter.append("static ");
  }

  @Override
  public void visitAbstractModifier(AbstractModifier abstractModifier) {
    printWriter.append("abstract ");
  }

  @Override
  public void visitLiteralStringExpr(
      LiteralStringExpression literalStringExpression) {
    printWriter.append("\"");
    printWriter.append(literalStringExpression.getValue());
    printWriter.append("\"");
  }

  @Override
  public void visitPrintStatement(PrintStatement printStatement) {
    printWriter.print("System.out.print(");
    for (Expression e : printStatement.getExpressionList()) {
      e.receive(this);
    }
    printWriter.append(");");
  }
}
