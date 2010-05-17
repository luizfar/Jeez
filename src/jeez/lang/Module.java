package jeez.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Module extends JeezObject implements MessageReceiver {
  
  public static final String ANONYMOUS_FUNCTION_NAME = "__main";
  
  private String __name;
  
  private Map<String, Function> functions = new HashMap<String, Function>();
  
  public Module(String name) {
    this.__name = name;
  }
  
  public String getName() {
    return __name;
  }
  
  public void addToFunctions(Function function) {
    functions.put(function.getName(), function);
  }
  
  public Function getFunction(String name) {
    return functions.get(name);
  }

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    Function function = functions.get(messageName);
    if (function == null) {
      throw new RuntimeException("Could not find message named '" + messageName + "'");
    }
    return function.execute(this, arguments, context);
  }
}