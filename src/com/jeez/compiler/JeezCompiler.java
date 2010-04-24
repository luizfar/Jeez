package com.jeez.compiler;

import java.io.PrintWriter;

import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.output.JavaGeneratorVisitor;
import com.jeez.compiler.output.JeezPrintWriter;
import com.jeez.compiler.parser.JeezParser;

public class JeezCompiler {
  
  private static final String CODE = "class Dog { private abstract int age public boolean alive static void bark(int volume) { print \"woof\" } }";
  
  private JeezLexer lexer;
  
  private JeezParser parser;
  
  public static void main(String[] args) {
    new JeezCompiler().compile(CODE.toCharArray());
  }
  
  public void compile(char[] input) {
    lexer = new JeezLexer(input);
    parser = new JeezParser(lexer);
    
    JeezSource root = parser.start();
    
    outputToScreen(root);
  }
  
  private void outputToScreen(JeezSource root) {
    JeezPrintWriter printWriter = new JeezPrintWriter();
    printWriter.set(new PrintWriter(System.out));
    
    JavaGeneratorVisitor visitor = new JavaGeneratorVisitor(printWriter);
    visitor.visitSourceUnit(root);
    
    printWriter.out.flush();
  }
}
