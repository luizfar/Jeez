package com.jeez.compiler.ast;

import com.jeez.compiler.ast.type.BooleanType;
import com.jeez.compiler.ast.type.IntegerType;
import com.jeez.compiler.ast.type.VoidType;

public interface Type extends ASTNode {

  public static final Type INT_TYPE = new IntegerType();
  
  public static final Type BOOLEAN_TYPE = new BooleanType();
  
  public static final Type VOID_TYPE = new VoidType();
}
