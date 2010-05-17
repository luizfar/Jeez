package jeez.lang.statement;

import static jeez.lang.java.JeezBoolean.TRUE;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class IfStatement implements Statement {

  private Expression expression;
  
  private Statement ifStatement;
  
  private Statement elseStatement;
  
  public IfStatement(Expression expression, Statement ifStatement) {
    this.expression = expression;
    this.ifStatement = ifStatement;
  }
  
  public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
    this.expression = expression;
    this.ifStatement = ifStatement;
    this.elseStatement = elseStatement;
  }
  
  @Override
  public void execute(ExecutionContext symbolTable) {
    if (TRUE.equals(expression.evaluate(symbolTable))) {
      ifStatement.execute(symbolTable);
    } else if (elseStatement != null) {
      elseStatement.execute(symbolTable);
    }
  }
}
