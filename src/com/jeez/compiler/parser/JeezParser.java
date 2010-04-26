package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;

import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.JeezSource;
import com.jeez.compiler.ast.JeezSourceMember;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.SymbolTable;
import com.jeez.compiler.ast.Type;
import com.jeez.compiler.ast.type.JavaClassType;
import com.jeez.compiler.ast.type.JeezClassType;
import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {
  
  private JeezLexer lexer;
  
  private JeezSource source;
  
  private SymbolTable symbolTable;
  
  private JeezClassParser classParser;
  
  private JeezMethodBodyParser methodBodyParser;
  
  public JeezParser(JeezLexer lexer) {
    this.lexer = lexer;
    
    symbolTable = new SymbolTable();
    
    classParser = new JeezClassParser(this);
    methodBodyParser = new JeezMethodBodyParser(this, symbolTable);
    
    source = new JeezSource();
  }
  
  public JeezSource start() {
    lexer.nextToken();
    
    while (lexer.token == CLASS) {
      JeezClass clazz = classParser.parseClass();
      symbolTable.putInGlobalScope(clazz);
      source.addMember(clazz);
    }
    
    parseMethodBodies(source);
    
    return source;
  }
  
  private void parseMethodBodies(JeezSource source) {
    for (JeezSourceMember member : source.getMembers()) {
      if (member instanceof JeezClass) {
        JeezClass clazz = (JeezClass) member;
        for (Method method : clazz.getMethods()) {
          lexer.resetTo(method.getBodyLocation());
          lexer.nextToken();
          methodBodyParser.parseMethodBody(method);
        }
      }
    }
  }
  
  Symbol getToken() {
    return lexer.token;
  }
  
  void nextToken() {
    lexer.nextToken();
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
        result = parseIdentifierAsType(); break;
        
      default:
        throw new JeezParserException("'int', 'boolean', 'void' or type expected", lexer.getLineNumber());  
    }
    lexer.nextToken();
    
    return result;
  }
  
  private Type parseIdentifierAsType() {
    String className = lexer.getStringValue();
    JeezClass jeezClass = symbolTable.getFromGlobalScope(className);
    if (jeezClass == null) {
      try {
        return new JavaClassType(Class.forName(className));
      } catch (ClassNotFoundException exception) {
        throw new JeezParserException("Class '" + className + "' not found", lexer.getLineNumber());
      }
    }
    
    return new JeezClassType(jeezClass);
  }
}
