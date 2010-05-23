package jeez.lang;

import java.util.ArrayList;
import java.util.List;

import jeez.interpreter.execution.ExecutionContext;
import jeez.lang.expression.Expression;
import jeez.lang.expression.ReturnExpression;

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
  
  public List<Variable> getParameters() {
    return parameters;
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
  
  public Object execute(Object target, List<Expression> arguments, ExecutionContext context) {
    context.addLocalContext();
    context.setSelfContext(target);

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
    
    Object result = null;
    for (Expression expression : block.getExpressions()) {
      Object value = expression.evaluate(context);
      if (expression instanceof ReturnExpression) {
        result = value;
        break;
      }
    }

    context.setSelfContext(null);
    context.removeLocalContext();
    
    return result;
  }
}
