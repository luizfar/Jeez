package com.jeez.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class JeezClass implements JeezSourceMember {

  private String name;
  
  private List<ClassMember> members = new ArrayList<ClassMember>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public void addMember(ClassMember member) {
    members.add(member);
  }
  
  public List<ClassMember> getMembers() {
    return members;
  }

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitClass(this);
  }
}
