package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.CLASS;
import static com.jeez.compiler.lexer.Symbol.IDENTIFIER;
import static com.jeez.compiler.lexer.Symbol.MODULE;
import static jeez.lang.Module.ANONYMOUS_FUNCTION_NAME;
import static jeez.lang.Type.VOID;
import jeez.lang.Clazz;
import jeez.lang.Function;
import jeez.lang.Module;
import jeez.lang.Type;
import jeez.lang.context.ExecutionContext;

import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.lexer.Symbol;

public class JeezParser {
  
  private JeezLexer lexer;
  
  private ClassParser classParser;
  
  private ModuleParser moduleParser;
  
  private ExecutionContext context;
  
  private String anonymousModuleName;
  
  public JeezParser(String sourceFileName, JeezLexer lexer, ExecutionContext context) {
    this.lexer = lexer;
    this.context = context;
    this.anonymousModuleName = sourceFileName + "_module";
    
    classParser = new ClassParser(this, context);
    moduleParser = new ModuleParser(this);
  }
  
  public void start() {
    lexer.nextToken();
    
    while (lexer.token != Symbol.EOF) {
      if (lexer.token == CLASS) {
        context.addClass(classParser.parseClass());
      } if (lexer.token == MODULE) {
        context.addModule(moduleParser.parseModule());
      } else {
        Module anonymous = context.getModule(anonymousModuleName);
        if (anonymous == null) {
          anonymous = createAnonymousModule(anonymousModuleName);
          context.addModule(anonymous);
        }
        moduleParser.parseAnonymousModule(anonymous);
      }
    }
  }
  
  private Module createAnonymousModule(String anonymousModuleName) {
    Module module = new Module(anonymousModuleName);
    module.addToFunctions(new Function(VOID, ANONYMOUS_FUNCTION_NAME));
    return module;
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
      case VOID:
        result = Type.VOID;
        break;
        
      case DEF:
        result = Type.DUCK;
        break;
        
      case IDENTIFIER:
        result = parseIdentifierAsType(); break;
        
      default:
        throw new ParserException("'int', 'boolean', 'void' or type expected", lexer.getLineNumber());  
    }
    lexer.nextToken();
    
    return result;
  }
  
  private Clazz parseIdentifierAsType() {
    return new Clazz(lexer.getStringValue());
  }
}
