package com.jeez.compiler.ast.stmt;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.expr.Expression;

public class IfStatement implements Statement {

  private Expression expression;
  
  private Statement ifStatement;
  private Statement elseStatement;

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public Statement getIfStatement() {
    return ifStatement;
  }

  public void setIfStatement(Statement ifStatement) {
    this.ifStatement = ifStatement;
  }

  public Statement getElseStatement() {
    return elseStatement;
  }

  public void setElseStatement(Statement elseStatement) {
    this.elseStatement = elseStatement;
  }

  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
