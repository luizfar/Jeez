package com.jeez.compiler;

import com.jeez.compiler.ast.ASTElement;

public class JeezCompiler {
  
  private static final String CODE = "class Dog { void bark() { print \"woof\" } }";
  
  public static void main(String[] args) {
    new JeezCompiler().compile(CODE.toCharArray());
  }
  
  public ASTElement compile(char[] input) {
    return null;
  }
}
