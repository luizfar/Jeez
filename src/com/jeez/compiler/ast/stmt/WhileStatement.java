package com.jeez.compiler.ast.stmt;

import com.jeez.compiler.ast.JeezCodeVisitor;
import com.jeez.compiler.ast.expr.Expression;

public class WhileStatement implements Statement {

  private Expression expression;
  
  private Statement statement;

  public void setStatement(Statement statement) {
    this.statement = statement;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public Expression getExpression() {
    return expression;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    // TODO luiz
  }
}
