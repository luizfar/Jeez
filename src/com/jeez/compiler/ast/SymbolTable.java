package com.jeez.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

  private Map<String, Variable> localTable = new HashMap<String, Variable>();
  
  public void putInLocalScope(Variable var) {
    localTable.put(var.getName(), var);
  }
  
  public Variable getInLocalScope(String name) {
    return localTable.get(name);
  }
  
  public void clearLocalScope() {
    localTable.clear();
  }
}
