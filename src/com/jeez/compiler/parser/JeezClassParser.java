package com.jeez.compiler.parser;

import static com.jeez.compiler.ast.VisibilityModifier.*;
import static com.jeez.compiler.lexer.Symbol.*;

import java.util.HashMap;
import java.util.Map;

import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.InstanceVariable;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezSourceMember;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
import com.jeez.compiler.ast.MethodParameterList;
import com.jeez.compiler.ast.Type;
import com.jeez.compiler.ast.VisibilityModifier;
import com.jeez.compiler.lexer.Symbol;

public class JeezClassParser {

  private static final Map<Symbol, VisibilityModifier> VISIBILITY_MODIFIERS = new HashMap<Symbol, VisibilityModifier>();
  
  static {
    VISIBILITY_MODIFIERS.put(PUBLIC, PUBLIC_MODIFIER);
    VISIBILITY_MODIFIERS.put(PROTECTED, PROTECTED_MODIFIER);
    VISIBILITY_MODIFIERS.put(PRIVATE, PRIVATE_MODIFIER);
  }

  private JeezParser jeezParser;
  
  public JeezClassParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
  }
  
  JeezSourceMember parseClass() {
    jeezParser.expect(CLASS);
    
    JeezClass clazz = new JeezClass();
    clazz.setName(jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      clazz.addMember(parseClassMember());
    }
    
    jeezParser.expect(RIGHT_CUR_BRACKET);
    
    return clazz;
  }

  ClassMember parseClassMember() {
    VisibilityModifier visibilityModifier = parseVisibilityModifier();
    boolean isStatic = false;
    
    if (jeezParser.getToken() == STATIC) {
      jeezParser.nextToken();
      isStatic = true;
    }
    
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {      
      Method method = parseMethod();
      method.setVisibilityModifier(visibilityModifier);
      method.setStatic(isStatic);
      method.setReturnType(type);
      method.setName(name);
      
      return method;
    }
    
    InstanceVariable attribute = new InstanceVariable();
    attribute.setVisibilityModifier(visibilityModifier);
    attribute.setType(type);
    attribute.setName(name);
    
    return attribute;
  }
  
  VisibilityModifier parseVisibilityModifier() {
    VisibilityModifier modifier = PACKAGE_MODIFIER;
    if (VISIBILITY_MODIFIERS.containsKey(jeezParser.getToken())) {
      modifier = VISIBILITY_MODIFIERS.get(jeezParser.getToken());
      jeezParser.nextToken();
    }
    return modifier;
  }
  
  Method parseMethod() {
    Method method = new Method();
    
    method.setParameters(parseParameterList());
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      jeezParser.nextToken();
    }
    jeezParser.nextToken();
    
    return method;
  }
  
  MethodParameterList parseParameterList() {
    jeezParser.expect(LEFT_PAR);
    
    MethodParameterList parameters = new MethodParameterList();
    if (jeezParser.getToken() == RIGHT_PAR) {
      jeezParser.nextToken();
      return parameters;
    }
    
    do {
      parameters.addParameter(parseParameter());
    }
    while (jeezParser.getToken() == COMMA);
    
    jeezParser.expect(RIGHT_PAR);
    
    return parameters;
  }
  
  MethodParameter parseParameter() {
    MethodParameter parameter = new MethodParameter();
    parameter.setType(jeezParser.parseTypeExcludesVoid());
    parameter.setName(jeezParser.parseIdentifier());
    
    return parameter;
  }
}
