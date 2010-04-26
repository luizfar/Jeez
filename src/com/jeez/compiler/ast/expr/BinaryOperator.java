package com.jeez.compiler.ast.expr;

import com.jeez.compiler.ast.ASTNode;
import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.lexer.Symbol;

public class BinaryOperator implements ASTNode {

  private Symbol symbol;
  
  public BinaryOperator(Symbol symbol) {
    this.symbol = symbol;
  }
  
  public Symbol getSymbol() {
    return symbol;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitBinaryOperator(this);
  }
}
