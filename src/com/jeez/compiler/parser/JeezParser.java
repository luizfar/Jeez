package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.CLASS;
import static com.jeez.compiler.lexer.Symbol.EOF;
import static com.jeez.compiler.lexer.Symbol.IDENTIFIER;
import static com.jeez.compiler.lexer.Symbol.MODULE;
import jeez.lang.Type;

import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {
  
  private JeezLexer lexer;
  
  private ClassParser classParser;
  
  private ModuleParser moduleParser;
  
  private ExpressionParser expressionParser;
  
  public JeezParser(JeezLexer lexer) {
    this.lexer = lexer;
    
    classParser = new ClassParser(this);
    moduleParser = new ModuleParser(this);
    expressionParser = new ExpressionParser(this);
    
    lexer.nextToken();
  }
  
  public Object parseNext() {
    if (lexer.token == CLASS) {
      return classParser.parseClass();
    }
    if (lexer.token == MODULE) {
      return moduleParser.parseModule();
    }
    if (lexer.token == EOF) {
      return EOF;
    }
    return expressionParser.parseExpression();
  }
  
  Symbol getToken() {
    return lexer.token;
  }
  
  void nextToken() {
    lexer.nextToken();
  }
  
  boolean foundEndOfExpression() {
    return lexer.foundEndOfExpression();
  }

  String getStringValue() {
    return lexer.getStringValue();
  }
  
  String getStringLiteral() {
    return lexer.getLiteralStringValue();
  }
  
  int getIntegerValue() {
    return lexer.getIntegerValue();
  }
  
  int getLineNumber() {
    return lexer.getLineNumber();
  }
  
  int getTokenPosition() {
    return lexer.getTokenPosition();
  }
  
  public void expect(Symbol symbol) {
    if (lexer.token != symbol) {
      throw new ParserException("'" + symbol + "' expected", lexer.getLineNumber());
    }
    lexer.nextToken();
  }
  
  public String parseIdentifier() {
    if (lexer.token != IDENTIFIER) {
      throw new ParserException("Identifier expected", lexer.getLineNumber());
    }
    String identifier = lexer.getStringValue();
    lexer.nextToken();
    
    return identifier;
  }
  
  public Type parseTypeExcludesVoid() {
    Type type = parseType();
    if (type == Type.VOID) {
      throw new ParserException("'void' is not a valid variable type", lexer.getLineNumber());
    }
    return type;
  }
  
  public Type parseType() {
    Type result;
    
    switch (lexer.token) {
      case DEF:
        result = Type.DUCK;
        break;
        
      default:
        throw new ParserException("'int', 'boolean', 'void' or type expected", lexer.getLineNumber());  
    }
    lexer.nextToken();
    
    return result;
  }
}
