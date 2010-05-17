package jeez.lang.statement;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.context.ExecutionContext;

public class CompositeStatement implements Statement {

  private List<Statement> statementList = new ArrayList<Statement>();
  
  public void addToStatementList(Statement statement) {
    statementList.add(statement);
  }
  
  @Override
  public void execute(ExecutionContext context) {
    for (Statement statement : statementList) {
      statement.execute(context);
    }
  }
}
