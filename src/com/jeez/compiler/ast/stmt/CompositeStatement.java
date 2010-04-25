package com.jeez.compiler.ast.stmt;

import com.jeez.compiler.ast.JeezCodeVisitor;

public class CompositeStatement implements Statement {

  private StatementList statementList;

  public CompositeStatement(StatementList statementList) {
    this.statementList = statementList;
  }
  
  public StatementList getStatementList() {
    return statementList;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
