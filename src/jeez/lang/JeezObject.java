package jeez.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JeezObject {
  
  private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
  
  private Map<String, Method> methods = new HashMap<String, Method>();

  JeezClass clazz;
  
  public JeezClass getJeezClass() {
    return clazz;
  }
  
  public void addToAttributes(Attribute attribute) {
    attributes.put(attribute.getName(), attribute);
  }
  
  public Attribute getAttribute(String name) {
    return attributes.get(name);
  }
  
  public Collection<Attribute> getAttributes() {
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
}
