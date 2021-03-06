package jeez.lang;

import static jeez.interpreter.execution.Bootstrap.getModuleClass;

import java.util.HashMap;
import java.util.Map;

public class Module extends JeezObject {
  
  private String name;
  
  private Map<String, Function> functions = new HashMap<String, Function>();
  
  public Module(String name) {
    super(getModuleClass());
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
