package com.jeez.compiler.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.jeez.compiler.ast.ASTNode;
import com.jeez.compiler.ast.JeezCodeVisitor;

public class StatementList implements ASTNode {

  private List<Statement> statements = new ArrayList<Statement>();
  
  public void addStatement(Statement statement) {
    statements.add(statement);
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    for (Statement statement : statements) {
      statement.receive(visitor);
    }
  }  
}
