package jeez.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeez.lang.bootstrap.Classes;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

public class Clazz extends JeezObject implements Type, MessageReceiver {

  private String name;
  
  private Map<String, ClassAttribute> classAttributes = new HashMap<String, ClassAttribute>();
  
  private Map<String, ClassMethod> classMethods = new HashMap<String, ClassMethod>();
  
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
  
  public void addToClassAttributes(ClassAttribute attribute) {
    classAttributes.put(attribute.getVariable().getName(), attribute);
  }
  
  public ClassAttribute getClassAttribute(String name) {
    return classAttributes.get(name);
  }
  
  public Collection<ClassAttribute> getClassAttributes() {
    return classAttributes.values();
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
  
  public void addToClassMethods(ClassMethod method) {
    classMethods.put(method.getName(), method);
  }
  
  public ClassMethod getClassMethod(String name) {
    return classMethods.get(name);
  }
  
  public Collection<ClassMethod> getClassMethods() {
    return classMethods.values();
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

  @Override
  public JeezObject receiveMessage(String messageName, List<Expression> arguments, ExecutionContext context) {
    ClassMethod method = classMethods.get(messageName);
    if (method == null) {
      throw new UnknownMessageException(Classes.CLASS, messageName);
    }
    return method.execute(this, arguments, context);
  }
  
  public String toString() {
    return "Class " + getName();
  }
  
  public void load(ExecutionContext context) {
    for (ClassAttribute attr : getClassAttributes()) {
      attr.init(context);
    }
  }
}
