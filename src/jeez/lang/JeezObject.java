package jeez.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JeezObject {
  
  private Map<String, Variable> attributes = new HashMap<String, Variable>();
  
  private Map<String, Method> methods = new HashMap<String, Method>();

  protected JeezClass clazz;
  
  public JeezObject() {
  }
  
  public JeezObject(JeezClass clazz) {
    this.clazz = clazz;
  }
  
  public JeezClass getJeezClass() {
    return clazz;
  }
  
  public void addToAttributes(Variable attribute) {
    attributes.put(attribute.getName(), attribute);
  }
  
  public Variable getAttribute(String name) {
    return attributes.get(name);
  }
  
  public Collection<Variable> getAttributes() {
    return attributes.values();
  }
  
  public void addToMethods(Method method) {
    methods.put(method.getName(), method);
  }
  
  public Method getMethod(String name) {
    return methods.get(name);
  }
  
  public Collection<Method> getMethods() {
    return methods.values();
  }
  
  public Method findMethod(String name) {
    Method method = getMethod(name);
    if (method != null) {
      return method;
    }
    
    JeezClass clazz = getJeezClass();
    JeezClass superClazz = clazz.getSuperClass();
    
    do {
      method = clazz.getClassMethod(name);
      if (method != null) {
        return method;
      }
      clazz = superClazz;
      superClazz = clazz.getSuperClass();
    } while (clazz != superClazz && clazz != null);
    
    method = clazz.getClassMethod(name);
    
    return method;
  }
}
