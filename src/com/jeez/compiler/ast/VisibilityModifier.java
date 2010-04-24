package com.jeez.compiler.ast;

import com.jeez.compiler.ast.visibility.PackageModifier;
import com.jeez.compiler.ast.visibility.PrivateModifier;
import com.jeez.compiler.ast.visibility.ProtectedModifier;
import com.jeez.compiler.ast.visibility.PublicModifier;

public interface VisibilityModifier extends ASTNode {

  public static final VisibilityModifier PUBLIC_MODIFIER = new PublicModifier();
  
  public static final VisibilityModifier PROTECTED_MODIFIER = new ProtectedModifier();
  
  public static final VisibilityModifier PRIVATE_MODIFIER = new PrivateModifier();
  
  public static final VisibilityModifier PACKAGE_MODIFIER = new PackageModifier();
}
