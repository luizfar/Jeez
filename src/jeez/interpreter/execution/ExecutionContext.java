package jeez.interpreter.execution;

import static jeez.lang.JeezString.STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeez.lang.JeezClass;
import jeez.lang.JeezObject;
import jeez.lang.Module;
import jeez.lang.Variable;

public class ExecutionContext {
  
  private Map<String, JeezClass> classes = new HashMap<String, JeezClass>();
  
  private Map<String, Module> modules = new HashMap<String, Module>();
  
  private List<Map<String, Variable>> localContexts = new ArrayList<Map<String, Variable>>();
  
  private JeezObject self;
  
  public void prepare() {
    addLocalContext();
    addClass(STRING);
  }
  
  public void addClass(JeezClass clazz) {
    classes.put(clazz.getName(), clazz);
    addToLocalContext(new Variable(clazz.getName(), clazz));
  }
  
  public JeezClass getClass(String name) {
    return classes.get(name);
  }
  
  public void addModule(Module module) {
    modules.put(module.getName(), module);
    addToLocalContext(new Variable(module.getName(), module));
  }
  
  public Module getModule(String name) {
    return modules.get(name);
  }
  
  public void addToLocalContext(Variable variable) {
    localContexts.get(localContexts.size() - 1).put(variable.getName(), variable);
  }
  
  public Variable getFromAnyContext(String name) {
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
  
  public void setSelfContext(JeezObject self) {
    this.self = self;
  }

  public JeezObject getSelfContext() {
    return self;
  }
}
