package com.jeez.compiler.output;

import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.InstanceVariable;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.ast.JeezSourceMember;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
import com.jeez.compiler.ast.MethodParameterList;
import com.jeez.compiler.ast.Variable;
import com.jeez.compiler.ast.expr.BinaryExpression;
import com.jeez.compiler.ast.expr.BinaryOperator;
import com.jeez.compiler.ast.expr.Expression;
import com.jeez.compiler.ast.expr.IntegerExpression;
import com.jeez.compiler.ast.expr.LiteralStringExpression;
import com.jeez.compiler.ast.expr.VariableExpression;
import com.jeez.compiler.ast.modifier.AbstractModifier;
import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.ast.modifier.PublicModifier;
import com.jeez.compiler.ast.modifier.StaticModifier;
import com.jeez.compiler.ast.modifier.visibility.PackageModifier;
import com.jeez.compiler.ast.modifier.visibility.PrivateModifier;
import com.jeez.compiler.ast.modifier.visibility.ProtectedModifier;
import com.jeez.compiler.ast.stmt.CompositeStatement;
import com.jeez.compiler.ast.stmt.IfStatement;
import com.jeez.compiler.ast.stmt.PrintStatement;
import com.jeez.compiler.ast.stmt.Statement;
import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.JeezClassType;
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
    method.getParameterList().receive(this);
    printWriter.appendln(" {");
    printWriter.add();
    
    method.getStatementList().receive(this);
    
    printWriter.sub();
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
  public void visitBooleanType(BooleanType booleanType) {
    printWriter.append("boolean");
  }

  @Override
  public void visitIntegerType(IntegerType integerType) {
    printWriter.append("int");
  }
  
  @Override
  public void visitVoidType(VoidType voidType) {
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
    printWriter.appendln(");");
  }

  @Override
  public void visitIfStatement(IfStatement ifStatement) {
    printWriter.print("if (");
    ifStatement.getExpression().receive(this);
    printWriter.appendln(") {");
    printWriter.add();
    ifStatement.getIfStatement().receive(this);
    printWriter.sub();
    printWriter.println("}");
    
    if (ifStatement.getElseStatement() != null) {
      printWriter.println("else {");
      printWriter.add();
      ifStatement.getElseStatement().receive(this);
      printWriter.sub();
      printWriter.println("}");
    }
  }

  @Override
  public void visitCompositeStatement(CompositeStatement compositeStatement) {
    printWriter.println("{");
    printWriter.add();
    for (Statement statement : compositeStatement.getStatementList().getStatements()) {
      statement.receive(this);
    }
    printWriter.sub();
    printWriter.println("}");
  }

  @Override
  public void visitBinaryExpression(BinaryExpression binaryExpression) {
    binaryExpression.getLeftSide().receive(this);
    binaryExpression.getOperator().receive(this);
    binaryExpression.getRightSide().receive(this);
  }

  @Override
  public void visitVariableExpression(VariableExpression variableExpression) {
    printWriter.append(variableExpression.getVariable().getName());
  }

  @Override
  public void visitIntegerExpression(IntegerExpression integerExpression) {
    printWriter.append(Integer.toString(integerExpression.getValue()));
  }

  @Override
  public void visitBinaryOperator(BinaryOperator binaryOperator) {
    // TODO luiz refactor this
    String operatorCode = null;
    switch (binaryOperator.getSymbol()) {
      case GREATER_THAN:
        operatorCode = " > "; break;
      
      case GREATER_EQUAL:
        operatorCode = " >= "; break;
        
      case LESS_THAN:
        operatorCode = " < "; break;
        
      case LESS_EQUAL:
        operatorCode = " <= "; break;
    }
    
    printWriter.append(operatorCode.toString());
  }

  @Override
  public void visitJeezClassType(JeezClassType jeezClassType) {
    printWriter.append(jeezClassType.getClazz().getName());
  }
}
