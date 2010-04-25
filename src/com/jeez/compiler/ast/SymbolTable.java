package com.jeez.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

  private Map<String, JeezClass> globalTable = new HashMap<String, JeezClass>();
  
  private Map<String, Variable> localTable = new HashMap<String, Variable>();
  
  public void putInGlobalScope(JeezClass clazz) {
    globalTable.put(clazz.getName(), clazz);
  }
  
  public JeezClass getFromGlobalScope(String name) {
    return globalTable.get(name);
  }
  
  public void putInLocalScope(Variable var) {
    localTable.put(var.getName(), var);
  }
  
  public Variable getFromLocalScope(String name) {
    return localTable.get(name);
  }
  
  public void clearLocalScope() {
    localTable.clear();
  }
}
