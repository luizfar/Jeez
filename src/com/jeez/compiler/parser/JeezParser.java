package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;

import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.ast.Type;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {
  
  private JeezLexer lexer;
  
  private JeezSource sourceUnit;
  
  private JeezClassParser classParser;
  
  public JeezParser(JeezLexer lexer) {
    this.lexer = lexer;
    classParser = new JeezClassParser(this);
    
    sourceUnit = new JeezSource();
  }
  
  public JeezSource start() {
    lexer.nextToken();
    switch (lexer.token) {
      case CLASS:
        sourceUnit.addMember(classParser.parseClass());
    }
    
    return sourceUnit;
  }
  
  public Symbol getToken() {
    return lexer.token;
  }
  
  public void nextToken() {
    lexer.nextToken();
  }
  
  public void expect(Symbol symbol) {
    if (lexer.token != symbol) {
      throw new JeezParserException("'" + symbol + "' expected", lexer.getLineNumber());
    }
    lexer.nextToken();
  }
  
  public String parseIdentifier() {
    if (lexer.token != IDENTIFIER) {
      throw new JeezParserException("Identifier expected", lexer.getLineNumber());
    }
    String identifier = lexer.getStringValue();
    lexer.nextToken();
    
    return identifier;
  }
  
  public Type parseTypeExcludesVoid() {
    Type type = parseType();
    if (type == Type.VOID_TYPE) {
      throw new JeezParserException("'void' is not a valid variable type", lexer.getLineNumber());
    }
    return type;
  }
  
  public Type parseType() {
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
