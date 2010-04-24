package com.jeez.compiler.ast;

import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;
import com.jeez.compiler.output.JeezPrintWriter;

public class JavaGeneratorVisitor implements JeezCodeVisitor {

  private JeezPrintWriter printWriter;
  
  public JavaGeneratorVisitor(JeezPrintWriter printWriter) {
    this.printWriter = printWriter;
  }

  @Override
  public void visitSourceUnit(SourceUnit sourceUnit) {
    for (SourceUnitMember member : sourceUnit.getMembers()) {
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
    printWriter.print("public void " + method.getName());
    method.getParameters().accept(this);
    printWriter.println("{");
    printWriter.add();
    
    printWriter.sub();
    printWriter.println("}");
  }
  
  @Override
  public void visitInstanceVariable(InstanceVariable variable) {
    visitVariable(variable);
  }

  @Override
  public void visitMethodParameter(MethodParameter parameter) {
    visitVariable(parameter);
  }
  
  @Override
  public void visitVariable(Variable variable) {
    variable.getType().accept(this);
    printWriter.print(" " + variable.getName() + ";");
  }

  @Override
  public void visitBoolean(BooleanType booleanType) {
    printWriter.print("boolean");
  }

  @Override
  public void visitInteger(IntegerType integerType) {
    printWriter.print("int");
  }
  
  @Override
  public void visitVoid(VoidType voidType) {
    printWriter.print("void");
  }

  @Override
  public void visitMethodParameterList(MethodParameterList methodParameterList) {
    printWriter.print("(");
    for (int i = 0; i < methodParameterList.getParameters().size(); i++) {
      MethodParameter parameter = methodParameterList.getParameters().get(i);
      parameter.accept(this);
      if (i != methodParameterList.getParameters().size() - 1) {
        printWriter.print(", ");
      }
    }
    printWriter.print(")");
  }
}
