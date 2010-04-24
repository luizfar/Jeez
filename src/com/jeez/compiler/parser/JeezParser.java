package com.jeez.compiler.parser;

import com.jeez.compiler.ast.ASTNode;
import com.jeez.compiler.ast.ClassMember;
import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.SourceUnit;
import com.jeez.compiler.ast.SourceUnitMember;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {

  private JeezLexer lexer;
  
  private SourceUnit sourceUnit;
  
  public JeezParser(JeezLexer lexer) {
    this.lexer = lexer;
    sourceUnit = new SourceUnit();
  }
  
  public ASTNode start() {
    lexer.nextToken();
    System.out.println(lexer.token);
    System.out.println(lexer.getInput());
    switch (lexer.token) {
      case CLASS: sourceUnit.addMember(parseClass());
    }
    
    return null;
  }
  
  SourceUnitMember parseClass() {
    expect(Symbol.CLASS);
    
    JeezClass clazz = new JeezClass();
    clazz.setName(parseIdentifier());
    
    expect(Symbol.LEFT_CUR_BRACKET);
    
    clazz.addMember(parseClassMember());
    
    expect(Symbol.RIGHT_CUR_BRACKET);
    
    return clazz;
  }
  
  ClassMember parseClassMember() {
    if (lexer.token == Symbol.VOID) {
      return parseMethod();
    }
    return null;
  }
  
  ClassMember parseMethod() {
    expect(Symbol.VOID);
    
    Method method = new Method();
    method.setName(parseIdentifier());
    
    expect(Symbol.LEFT_PAR);
    expect(Symbol.RIGHT_PAR);
    
    expect(Symbol.LEFT_CUR_BRACKET);
    while (lexer.token != Symbol.RIGHT_CUR_BRACKET) {
      lexer.nextToken();
    }
    lexer.nextToken();
    
    return method;
  }
  
  private void expect(Symbol symbol) {
    if (lexer.token != symbol) {
      throw new JeezParserException("'" + symbol + "' expected", lexer.getLineNumber());
    }
    lexer.nextToken();
  }
  
  private String parseIdentifier() {
    if (lexer.token != Symbol.IDENTIFIER) {
      throw new JeezParserException("Identifier expected", lexer.getLineNumber());
    }
    String identifier = lexer.getStringValue();
    lexer.nextToken();
    
    return identifier;
  }
}
