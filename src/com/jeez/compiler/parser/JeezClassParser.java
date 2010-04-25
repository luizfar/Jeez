package com.jeez.compiler.parser;

import static com.jeez.compiler.ast.modifier.visibility.VisibilityModifier.*;
import static com.jeez.compiler.lexer.Symbol.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.InstanceVariable;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezSourceMember;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
import com.jeez.compiler.ast.MethodParameterList;
import com.jeez.compiler.ast.Type;
import com.jeez.compiler.ast.modifier.ClassMemberModifier;
import com.jeez.compiler.lexer.Symbol;

public class JeezClassParser {
  
  private static final Map<Symbol, ClassMemberModifier> MEMBER_MODIFIERS = new HashMap<Symbol, ClassMemberModifier>();
  
  static {
    MEMBER_MODIFIERS.put(PUBLIC, PUBLIC_MODIFIER);
    MEMBER_MODIFIERS.put(PROTECTED, PROTECTED_MODIFIER);
    MEMBER_MODIFIERS.put(PRIVATE, PRIVATE_MODIFIER);
    MEMBER_MODIFIERS.put(STATIC, STATIC_MODIFIER);
    MEMBER_MODIFIERS.put(ABSTRACT, ABSTRACT_MODIFIER);
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
    Set<ClassMemberModifier> modifiers = new HashSet<ClassMemberModifier>();
    while (isClassMemberModifier(jeezParser.getToken())) {
      modifiers.add(parseClassMemberModifier());
    }
    
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    ClassMember member;
    
    if (jeezParser.getToken() == LEFT_PAR) {      
      member = parseMethod();
    } else {
      member = new InstanceVariable();
    }
    
    member.setType(type);
    member.setName(name);
    
    for (ClassMemberModifier modifier : modifiers) {
      if (member.accepts(modifier)) {
        member.addModifier(modifier);
      } else {
        throw new JeezParserException("Invalid modifier for member", jeezParser
            .getLineNumber());
      }
    }
    
    return member;
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
  
  ClassMemberModifier parseClassMemberModifier() {
    ClassMemberModifier modifier = MEMBER_MODIFIERS.get(jeezParser.getToken());
    if (modifier == null) {
      throw new JeezParserException(
          "'public', 'private', 'protected', 'static' or 'abstract' expected",
          jeezParser.getLineNumber());
    }
    jeezParser.nextToken();
    
    return modifier;
  }
  
  boolean isClassMemberModifier(Symbol token) {
    return MEMBER_MODIFIERS.containsKey(token);
  }
}
