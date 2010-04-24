package com.jeez.compiler.ast;

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
    printWriter.println("public void " + method.getName() + " {");
    printWriter.add();
    
    printWriter.sub();
    printWriter.println("}");
  }
}
