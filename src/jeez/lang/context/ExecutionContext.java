package jeez.lang.context;

import java.util.HashMap;
import java.util.Map;

import jeez.lang.Clazz;
import jeez.lang.Module;
import jeez.lang.Variable;

public class ExecutionContext {
  
  private Map<String, Clazz> classes = new HashMap<String, Clazz>();
  
  private Map<String, Module> modules = new HashMap<String, Module>();
  
  private Map<String, Variable> localContext = new HashMap<String, Variable>();
  
  public void addClass(Clazz clazz) {
    classes.put(clazz.getName(), clazz);
  }
  
  public Clazz getClass(String name) {
    return classes.get(name);
  }
  
  public void addModule(Module module) {
    modules.put(module.getName(), module);
  }
  
  public Module getModule(String name) {
    return modules.get(name);
  }
  
  public void addToLocalContext(Variable variable) {
    localContext.put(variable.getName(), variable);
  }
  
  public Variable getFromLocalContext(String name) {
    return localContext.get(name);
  }
}
