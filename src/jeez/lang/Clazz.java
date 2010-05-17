package jeez.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Clazz implements Type {

  private String name;
  
  private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
  
  private Map<String, Method> methods = new HashMap<String, Method>();
  
  public Clazz(String string) {
    this.name = string;
  }
  
  public String getName() {
    return name;
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
  
  public JeezObject newObject() {
    return new JeezObject(this);
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Clazz)) {
      return false;
    }
    
    return name.equals(((Clazz) object).name);
  }
  
  public int hashCode() {
    return name.hashCode();
  }
}
