package com.jeez.compiler;

import java.io.PrintWriter;

import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.output.JavaGeneratorVisitor;
import com.jeez.compiler.output.JeezPrintWriter;
import com.jeez.compiler.parser.JeezParser;

public class JeezCompiler {
  
  private static final String CODE = 
    "class Person {\n" +
    "  public int name\n" +
    "}\n" +
    "\n" +
    "class Dog {\n" +
    "  private int age\n" +
    "  public boolean alive\n" +
    "  private Person owner\n" +
    "  static void bark(int volume) {\n" +
    "    if (volume <= 10) {\n" +
    "      print \"woof\"\n" +
    "    } else {\n" +
    "      print \"WOOF\"\n" +
    "    }\n" +
    "  }\n" +
    " }";
  
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
    printWriter.set(new PrintWriter(System.out, true));
    
    JavaGeneratorVisitor visitor = new JavaGeneratorVisitor(printWriter);
    visitor.visitSourceUnit(root);
  }
}
