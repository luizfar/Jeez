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
      member.accept(this);
    }
  }
  
  @Override
  public void visitClass(JeezClass clazz) {
    printWriter.println("public class " + clazz.getName() + " {");
    printWriter.add();
    
    for (ClassMember member : clazz.getMembers()) {
      member.accept(this);
    }
    
    printWriter.sub();
    printWriter.println("}");
  }

  @Override
  public void visitMethod(Method method) {
    printWriter.print("public ");
    method.getReturnType().accept(this);
    printWriter.append(" " + method.getName() + " ");
    method.getParameters().accept(this);
    printWriter.appendln(" {");
    printWriter.add();
    
    printWriter.sub();
    printWriter.println("}");
  }
  
  @Override
  public void visitInstanceVariable(InstanceVariable variable) {
    printWriter.print("private ");
    variable.getType().accept(this);
    printWriter.appendln(" " + variable.getName() + ";");
  }

  @Override
  public void visitMethodParameter(MethodParameter parameter) {
    parameter.getType().accept(this);
    printWriter.append(" " + parameter.getName());
  }
  
  @Override
  public void visitVariable(Variable variable) {
    variable.getType().accept(this);
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
      parameter.accept(this);
      if (i != methodParameterList.getParameters().size() - 1) {
        printWriter.append(", ");
      }
    }
    printWriter.append(")");
  }
}
