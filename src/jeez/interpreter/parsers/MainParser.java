package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.CLASS;
import static jeez.interpreter.lexer.Symbol.EOF;
import static jeez.interpreter.lexer.Symbol.IDENTIFIER;
import static jeez.interpreter.lexer.Symbol.MODULE;
import jeez.interpreter.lexer.Lexer;
import jeez.interpreter.lexer.Symbol;
import jeez.lang.CustomType;
import jeez.lang.Type;

public class MainParser {
  
  private Lexer lexer;
  
  private ClassParser classParser;
  
  private ModuleParser moduleParser;
  
  private ExpressionParser expressionParser;
  
  public MainParser(Lexer lexer) {
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
    String identifier = new String(lexer.getStringValue());
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
        
      case VOID:
        result = Type.VOID;
        break;
        
      case IDENTIFIER:
        result = new CustomType(lexer.getStringValue());
        break;
        
      default:
        throw new ParserException("'def', 'void' or identifier expected", lexer.getLineNumber());  
    }
    lexer.nextToken();
    return result;
  }
}
