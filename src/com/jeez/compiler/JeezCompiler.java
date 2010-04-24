package com.jeez.compiler;

import com.jeez.compiler.ast.ASTNode;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.parser.JeezParser;

public class JeezCompiler {
  
  private static final String CODE = "class Dog { void bark() { print \"woof\" } }";
  
  private JeezLexer lexer;
  
  private JeezParser parser;
  
  public static void main(String[] args) {
    new JeezCompiler().compile(CODE.toCharArray());
  }
  
  public ASTNode compile(char[] input) {
    lexer = new JeezLexer(input);
    parser = new JeezParser(lexer);
    
    return parser.start();
  }
}
