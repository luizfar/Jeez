package com.jeez.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class JeezSource implements ASTNode {

  private List<JeezSourceMember> members = new ArrayList<JeezSourceMember>();
  
  public void addMember(JeezSourceMember member) {
    members.add(member);
  }
  
  public List<JeezSourceMember> getMembers() {
    return members;
  }

  @Override
  public void accept(JeezCodeVisitor visitor) {
    visitor.visitSourceUnit(this);
  }
}
