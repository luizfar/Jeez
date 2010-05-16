package jeez.lang.statement;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.context.ExecutionContext;

public class StatementList implements Statement {

  private List<Statement> statements = new ArrayList<Statement>();
  
  public void addStatement(Statement statement) {
    statements.add(statement);
  }
  
  @Override
  public void execute(ExecutionContext symbolTable) {
    for (Statement statement : statements) {
      statement.execute(symbolTable);
    }
  }
}
