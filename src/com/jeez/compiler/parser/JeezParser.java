package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;

import com.jeez.compiler.ast.InstanceVariable;
import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
import com.jeez.compiler.ast.MethodParameterList;
import com.jeez.compiler.ast.SourceUnit;
import com.jeez.compiler.ast.SourceUnitMember;
import com.jeez.compiler.ast.Type;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {

  private JeezLexer lexer;
  
  private SourceUnit sourceUnit;
  
  public JeezParser(JeezLexer lexer) {
    this.lexer = lexer;
    sourceUnit = new SourceUnit();
  }
  
  public SourceUnit start() {
    lexer.nextToken();
    switch (lexer.token) {
      case CLASS: sourceUnit.addMember(parseClass());
    }
    
    return sourceUnit;
  }
  
  SourceUnitMember parseClass() {
    expect(CLASS);
    
    JeezClass clazz = new JeezClass();
    clazz.setName(parseIdentifier());
    
    expect(LEFT_CUR_BRACKET);
    
    clazz.addMember(parseClassMember());
    
    expect(RIGHT_CUR_BRACKET);
    
    return clazz;
  }
  
  ClassMember parseClassMember() {
    Type type = parseType();
    String name = parseIdentifier();
    
    if (lexer.token == LEFT_PAR) {      
      return parseMethod(type, name);
    }
    
    InstanceVariable attribute = new InstanceVariable();
    attribute.setType(type);
    attribute.setName(name);
    
    return attribute;
  }
  
  ClassMember parseMethod(Type returnType, String name) {
    Method method = new Method();
    method.setReturnType(returnType);
    method.setName(name);
    
    method.setParameters(parseParameterList());
    
    expect(LEFT_CUR_BRACKET);
    
    while (lexer.token != RIGHT_CUR_BRACKET) {
      lexer.nextToken();
    }
    lexer.nextToken();
    
    return method;
  }
  
  MethodParameterList parseParameterList() {
    expect(LEFT_PAR);
    
    MethodParameterList parameters = new MethodParameterList();
    if (lexer.token == RIGHT_PAR) {
      lexer.nextToken();
      return parameters;
    }
    
    do {
      parameters.addParameter(parseParameter());
    }
    while (lexer.token == COMMA);
    
    expect(RIGHT_PAR);
    
    return parameters;
  }
  
  MethodParameter parseParameter() {
    MethodParameter parameter = new MethodParameter();
    parameter.setType(parseTypeExcludesVoid());
    parameter.setName(parseIdentifier());
    
    return parameter;    
  }
  
  private void expect(Symbol symbol) {
    if (lexer.token != symbol) {
      throw new JeezParserException("'" + symbol + "' expected", lexer.getLineNumber());
    }
    lexer.nextToken();
  }
  
  private String parseIdentifier() {
    if (lexer.token != IDENTIFIER) {
      throw new JeezParserException("Identifier expected", lexer.getLineNumber());
    }
    String identifier = lexer.getStringValue();
    lexer.nextToken();
    
    return identifier;
  }
  
  private Type parseTypeExcludesVoid() {
    Type type = parseType();
    if (type == Type.VOID_TYPE) {
      throw new JeezParserException("'void' is not a valid variable type", lexer.getLineNumber());
    }
    return type;
  }
  
  private Type parseType() {
    Type result;
    
    switch (lexer.token) {
      case INT:
        result = Type.INT_TYPE; break;
      
      case BOOLEAN:
        result = Type.BOOLEAN_TYPE; break;
        
      case VOID:
        result = Type.VOID_TYPE; break;
        
      case IDENTIFIER:
        throw new RuntimeException("Not yet implemented");
        
      default:
        throw new JeezParserException("'int', 'boolean', 'void' or type expected", lexer.getLineNumber());  
    }
    lexer.nextToken();
    
    return result;
  }
}
