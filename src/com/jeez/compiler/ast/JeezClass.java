package com.jeez.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class JeezClass implements JeezSourceMember {

  private String name;
  
  private List<InstanceVariable> instanceVariables = new ArrayList<InstanceVariable>();
  
  private List<Method> methods = new ArrayList<Method>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public void addMember(ClassMember member) {
    if (member instanceof InstanceVariable) {
      instanceVariables.add((InstanceVariable) member);
    } else if (member instanceof Method) {
      methods.add((Method) member);
    }
  }
  
  public List<ClassMember> getMembers() {
    List<ClassMember> members = new ArrayList<ClassMember>();
    members.addAll(instanceVariables);
    members.addAll(methods);
    
    return members;
  }
  
  public List<InstanceVariable> getInstanceVariables() {
    return instanceVariables;
  }
  
  public List<Method> getMethods() {
    return methods;
  }
  
  public List<Method> getMethod(String name, int argumentCount) {
    List<Method> results = new ArrayList<Method>();
    for (Method method : methods) {
      if (method.getName().equals(name)
          && method.getParameters().getSize() == argumentCount) {
        results.add(method);
      }
    }
    return results;
  }
  
  @Override
  public void receive(JeezCodeVisitor visitor) {
    visitor.visitClass(this);
  }
}
