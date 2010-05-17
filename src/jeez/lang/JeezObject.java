package jeez.lang;

import static jeez.lang.Type.DUCK;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JeezObject {

  private Clazz clazz;
  
  private Map<String, Variable> instanceVariables = new HashMap<String, Variable>();
  
  public JeezObject(Clazz clazz) {
    this.clazz = clazz;
    for (Attribute attribute : clazz.getAttributes()) {
      Variable var = attribute.getType() == DUCK ? 
          new DynamicVariable(attribute.getName()) :
          new TypedVariable((Clazz) attribute.getType(), attribute.getName());
      
      instanceVariables.put(var.getName(), var);
    }
  }
  
  public JeezObject() {
    
  }
  
  public Clazz getClazz() {
    return clazz;
  }
  
  public Collection<Variable> getInstanceVariables() {
    return instanceVariables.values();
  }
}
