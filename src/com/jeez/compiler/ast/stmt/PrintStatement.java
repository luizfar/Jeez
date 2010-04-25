package com.jeez.compiler.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.expr.Expression;

public class PrintStatement implements Statement {

  private List<Expression> expressionList = new ArrayList<Expression>();
  
  public void addExpr(Expression expr) {
    expressionList.add(expr);
  }
  
  public List<Expression> getExpressionList() {
    return expressionList;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitPrintStatement(this);
  }
}
