package jeez.lang;

import java.util.HashMap;
import java.util.Map;

public class Module {
  
  public static final String ANONYMOUS_FUNCTION_NAME = "__main";
  
  private String name;
  
  private Map<String, Function> functions = new HashMap<String, Function>();
  
  public Module(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void addToFunctions(Function function) {
    functions.put(function.getName(), function);
  }
  
  public Function getFunction(String name) {
    return functions.get(name);
  }
}
