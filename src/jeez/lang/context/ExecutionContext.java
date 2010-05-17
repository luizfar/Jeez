package jeez.lang.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeez.lang.Clazz;
import jeez.lang.MessageReceiver;
import jeez.lang.Module;
import jeez.lang.Variable;

public class ExecutionContext {
  
  private Map<String, Clazz> classes = new HashMap<String, Clazz>();
  
  private Map<String, Module> modules = new HashMap<String, Module>();
  
  private List<Map<String, Variable>> localContexts = new ArrayList<Map<String, Variable>>();
  
  public ExecutionContext() {
    addLocalContext();
  }
  
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
    localContexts.get(localContexts.size() - 1).put(variable.getName(), variable);
  }
  
  public Variable getFromLocalContext(String name) {
    for (int i = localContexts.size() - 1; i >= 0; i--) {
      Variable var = localContexts.get(i).get(name);
      if (var != null) {
        return var;
      }
    }
    return null;
  }
  
  public void addLocalContext() {
    localContexts.add(new HashMap<String, Variable>());
  }
  
  public void removeLocalContext() {
    localContexts.remove(localContexts.size() - 1);
  }
  
  public MessageReceiver getMessageReceiver(String name) {
    MessageReceiver receiver = getFromLocalContext(name);
    if (receiver == null) {
      receiver = getClass(name);
    }
    if (receiver == null) {
      receiver = getModule(name);
    }
    return receiver;
  }
}