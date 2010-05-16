package jeez.lang;

import jeez.lang.context.ExecutionContext;
import jeez.lang.statement.Statement;

public class Function {

  private Type type;
  
  private String name;
  
  private Block block;
  
  public Function(Type type, String name, Block block) {
    this.type = type;
    this.name = name;
    this.block = block;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }
  
  public Block getBlock() {
    return block;
  }
  
  public void execute(Object target, ExecutionContext context) {
    for (Statement statement : block.getStatements()) {
      statement.execute(context);
    }
  }
}
