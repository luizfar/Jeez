package jeez.lang.statement;

import jeez.lang.context.ExecutionContext;

public interface Statement {

  public void execute(ExecutionContext symbolTable);
}
