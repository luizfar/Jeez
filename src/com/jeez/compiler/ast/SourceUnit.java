package com.jeez.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class SourceUnit implements ASTNode {

  private List<SourceUnitMember> members = new ArrayList<SourceUnitMember>();
  
  public void addMember(SourceUnitMember member) {
    members.add(member);
  }
}
