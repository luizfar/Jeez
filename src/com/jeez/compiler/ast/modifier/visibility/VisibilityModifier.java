package com.jeez.compiler.ast.modifier.visibility;

import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.ast.modifier.PublicModifier;

public interface VisibilityModifier extends ClassMemberModifier {

  public static final VisibilityModifier PUBLIC_MODIFIER = new PublicModifier();
  
  public static final VisibilityModifier PROTECTED_MODIFIER = new ProtectedModifier();
  
  public static final VisibilityModifier PRIVATE_MODIFIER = new PrivateModifier();
  
  public static final VisibilityModifier PACKAGE_MODIFIER = new PackageModifier();
}
