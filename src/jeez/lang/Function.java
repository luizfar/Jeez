package jeez.lang;

import java.util.ArrayList;
import java.util.List;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;
import jeez.lang.statement.ReturnStatement;
import jeez.lang.statement.Statement;

public class Function {

  private Type type;
  
  private String name;
  
  private List<Variable> parameters = new ArrayList<Variable>();
  
  private Block block = new Block();
  
  public Function(Type type, String name) {
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }
  
  public void addToParameters(Variable variable) {
    parameters.add(variable);
  }
  
  public int getParametersCount() {
    return parameters.size();
  }
  
  public void setBlock(Block block) {
    this.block = block;
  }
  
  public Block getBlock() {
    return block;
  }
  
  public JeezObject execute(JeezObject target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();

    if (getParametersCount() != arguments.size()) {
      throw new RuntimeException("Wrong number of arguments for method '"
          + getName() + "' . Expected: " + getParametersCount()
          + ", received: " + arguments.size());
    }
    
    for (int i = 0; i < parameters.size(); i++) {
      Variable argument = parameters.get(i);
      argument.setValue(arguments.get(i).evaluate(context));
      context.addToLocalContext(argument);
    }
    
    JeezObject result = null;
    for (Statement statement : block.getStatements()) {
      statement.execute(context);
      if (statement instanceof ReturnStatement) {
        result = ((ReturnStatement) statement).getResult();
      }
    }
    
    context.removeLocalContext();
    
    return result;
  }
}
