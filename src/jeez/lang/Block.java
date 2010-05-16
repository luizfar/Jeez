package jeez.lang;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.statement.Statement;

public class Block {

  private List<Statement> statements = new ArrayList<Statement>();
  
  public void addToStatements(Statement statement) {
    statements.add(statement);
  }
  
  public List<Statement> getStatements() {
    return statements;
  }
}
