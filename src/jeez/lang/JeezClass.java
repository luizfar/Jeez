package jeez.lang;

import static jeez.interpreter.execution.Bootstrap.CLASS;
import static jeez.interpreter.execution.Bootstrap.OBJECT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JeezClass extends JeezObject implements Type {
  
  private JeezClass superClass = OBJECT;
  
  private String name;
  
  private Map<String, Variable> classAttributes = new HashMap<String, Variable>();
  
  private Map<String, Method> classMethods = new HashMap<String, Method>();
  
  public JeezClass(String string) {
    super(CLASS);
    this.name = string;
  }
  
  public String getName() {
    return name;
  }
  
  public void setSuperClass(JeezClass superClass) {
    this.superClass = superClass;
  }
  
  public JeezClass getSuperClass() {
    return superClass;
  }
  
  public void addToClassAttributes(Variable attribute) {
    classAttributes.put(attribute.getName(), attribute);
  }
  
  public Variable getClassAttribute(String name) {
    return classAttributes.get(name);
  }
  
  public Collection<Variable> getClassAttributes() {
    return classAttributes.values();
  }
  
  public void addToClassMethods(Method method) {
    classMethods.put(method.getName(), method);
  }
  
  public Method getClassMethod(String name) {
    return classMethods.get(name);
  }
  
  public Collection<Method> getClassMethods() {
    return classMethods.values();
  }
  
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof JeezClass)) {
      return false;
    }
    
    return name.equals(((JeezClass) object).name);
  }
  
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }
  
  @Override
  public java.lang.String toString() {
    return "Class " + getName();
  }
  
  public boolean isAssignableFrom(JeezClass clazz) {
    return equals(clazz);
  }
}
