package com.jeez.compiler;

import java.io.PrintWriter;

import com.jeez.compiler.ast.JavaGeneratorVisitor;
import com.jeez.compiler.ast.SourceUnit;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.output.JeezPrintWriter;
import com.jeez.compiler.parser.JeezParser;

public class JeezCompiler {
  
  private static final String CODE = "class Dog { void bark() { print \"woof\" } }";
  
  private JeezLexer lexer;
  
  private JeezParser parser;
  
  public static void main(String[] args) {
    new JeezCompiler().compile(CODE.toCharArray());
  }
  
  public void compile(char[] input) {
    lexer = new JeezLexer(input);
    parser = new JeezParser(lexer);
    
    SourceUnit root = parser.start();
    
    outputToScreen(root);
  }
  
  private void outputToScreen(SourceUnit root) {
    JeezPrintWriter printWriter = new JeezPrintWriter();
    printWriter.set(new PrintWriter(System.out));
    
    JavaGeneratorVisitor visitor = new JavaGeneratorVisitor(printWriter);
    visitor.visitSourceUnit(root);
    
    printWriter.out.flush();
  }
}
