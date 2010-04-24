package com.jeez.compiler.lexer;

import java.util.Hashtable;

public class Lexer {

  public Symbol token;
  private String stringValue, literalStringValue;
  private int numberValue;

  private int  tokenPos;
  
  private int lineNumber;
  
  private char []input;
  
  private static final Hashtable<String, Symbol> keywordsTable = new Hashtable<String, Symbol>();
  
  private static final Hashtable<String, Symbol> OPERATORS = new Hashtable<String, Symbol>();
  
  static {
    keywordsTable.put("true", Symbol.TRUE);
    keywordsTable.put("false", Symbol.FALSE);
    keywordsTable.put("static", Symbol.STATIC);
    keywordsTable.put("void", Symbol.VOID);
    keywordsTable.put("null", Symbol.NULL);
    keywordsTable.put("if", Symbol.IF);
    keywordsTable.put("else", Symbol.ELSE);
    keywordsTable.put("while", Symbol.WHILE);
    keywordsTable.put("read", Symbol.READ);
    keywordsTable.put("write", Symbol.WRITE);
    keywordsTable.put("break", Symbol.BREAK);
    keywordsTable.put("int", Symbol.INT);
    keywordsTable.put("boolean", Symbol.BOOLEAN);
    keywordsTable.put("return", Symbol.RETURN);
    keywordsTable.put("class", Symbol.CLASS);
    keywordsTable.put("super", Symbol.SUPER);
    keywordsTable.put("this", Symbol.THIS);
    keywordsTable.put("new", Symbol.NEW);
    keywordsTable.put("public", Symbol.PUBLIC);
    keywordsTable.put("private", Symbol.PRIVATE);
    keywordsTable.put("extends", Symbol.EXTENDS);
    
    OPERATORS.put("+", Symbol.PLUS);
    OPERATORS.put("-", Symbol.MINUS);
    OPERATORS.put("*", Symbol.MULTIPLIER);
    OPERATORS.put("/", Symbol.DIVISOR);
    OPERATORS.put("%", Symbol.REMAINDER);
    OPERATORS.put("<", Symbol.LESS_THAN);
    OPERATORS.put("<=", Symbol.LESS_EQUAL);
    OPERATORS.put(">", Symbol.GREATER_THAN);
    OPERATORS.put(">=", Symbol.GREATER_EQUAL);
    OPERATORS.put("=", Symbol.ASSIGN);
    OPERATORS.put("==", Symbol.EQUAL);
    OPERATORS.put("!=", Symbol.NOT_EQUAL);
    OPERATORS.put("!", Symbol.NOT);
    OPERATORS.put("(", Symbol.NOT_EQUAL);
    OPERATORS.put(")", Symbol.NOT_EQUAL);
    OPERATORS.put("{", Symbol.NOT_EQUAL);
    OPERATORS.put("}", Symbol.NOT_EQUAL);
    OPERATORS.put(",", Symbol.NOT_EQUAL);
    OPERATORS.put(";", Symbol.NOT_EQUAL);
    OPERATORS.put(".", Symbol.NOT_EQUAL);
    OPERATORS.put("&&", Symbol.NOT_EQUAL);
    OPERATORS.put("||", Symbol.NOT_EQUAL);
    OPERATORS.put("&&", Symbol.NOT_EQUAL);
    OPERATORS.put("&&", Symbol.NOT_EQUAL);
  }
  
  public Lexer(char []input) {
    this.input = input;
    // add an end-of-file label to make it easy to do the lexer
    input[input.length - 1] = '\0';
    
    // number of the current line
    lineNumber = 1;
    tokenPos = 0;
  }

  public void nextToken() {
    char ch;
    
    ch = findNextChar();
    
    if (ch == '\0') {
      token = Symbol.EOF;
    } else if (input[tokenPos] == '/' && input[tokenPos + 1] == '/') {
      skipSingleLineComment();
      nextToken();
    } else if (input[tokenPos] == '/' && input[tokenPos + 1] == '*') {
      skipMultipleLinesComment();
      nextToken();
    } else {
      if (isValidIdentifierChar(ch)) {
        parseKeywordOrIdentifier(ch);
      } else if (isDigit(ch)) {
        parseLiteralNumber(ch);
      } else if (ch == '"') {
        parseLiteralString();
      } else {
        parseOperator(ch);
      }
    }
  }

  private void parseLiteralNumber(char ch) {
    StringBuffer number = new StringBuffer();
    while (isDigit(input[tokenPos])) {
      number.append(input[tokenPos]);
      tokenPos++;
    }
    
    try {
      numberValue = Integer.valueOf(number.toString()).intValue();
    } catch (NumberFormatException e) {
      throw new LexerException("Number out of limits", lineNumber);
    }
    
    token = Symbol.NUMBER;
  }

  private void parseKeywordOrIdentifier(char ch) {
    StringBuffer identifier = new StringBuffer();
    while (isLetterOrDigit(ch)) {
      identifier.append(input[tokenPos]);
      tokenPos++;
    }
    
    stringValue = identifier.toString();
    Symbol keywordSymbol = keywordsTable.get(stringValue);
    if (keywordSymbol == null) {
      token = Symbol.IDENTIFIER;
    }
  }

  private void parseLiteralString() {
    StringBuffer s = new StringBuffer();
    while (input[tokenPos] != '\0' && input[tokenPos] != '\n')
      if (input[tokenPos] == '"')
        break;
      else if (input[tokenPos] == '\\') {
        if (input[tokenPos + 1] != '\n' && input[tokenPos + 1] != '\0') {
          s.append(input[tokenPos]);
          tokenPos++;
          s.append(input[tokenPos]);
          tokenPos++;
        } else {
          s.append(input[tokenPos]);
          tokenPos++;
        }
      } else {
        s.append(input[tokenPos]);
        tokenPos++;
      }

    if (input[tokenPos] == '\0' || input[tokenPos] == '\n') {
      literalStringValue = "";
      throw new LexerException("Unterminated string literal", lineNumber);
    } else {
      tokenPos++;
      literalStringValue = s.toString();
    }
    token = Symbol.LITERAL_STRING;
  }

  private void parseOperator(char ch) {
    tokenPos++;
    String operator = String.valueOf(ch) + input[tokenPos];
    token = OPERATORS.get(operator);
    if (token != null) {
      tokenPos++;
    } else {
      token = OPERATORS.get(String.valueOf(ch));
    }
  }

  private boolean isValidIdentifierChar(char ch) {
    return Character.isLetter(ch) || ch == '_';
  }

  private boolean isDigit(char ch) {
    return Character.isDigit(ch);
  }

  private boolean isLetterOrDigit(char ch) {
    return Character.isLetter(ch = input[tokenPos]) || isDigit(ch) || ch == '_';
  }

  private void skipMultipleLinesComment() {
    char ch;
    int lineNumberStartComment = lineNumber;
    tokenPos += 2;
    while ((ch = input[tokenPos]) != '\0'
        && (ch != '*' || input[tokenPos + 1] != '/')) {
      if (ch == '\n')
        lineNumber++;
      tokenPos++;
    }
    if (ch == '\0') {
      throw new LexerException("Comment opened and not closed", lineNumberStartComment);
    }
    else {
      tokenPos += 2;
    }
  }

  private void skipSingleLineComment() {
    while (input[tokenPos] != '\0' && input[tokenPos] != '\n')
      tokenPos++;
  }

  private char findNextChar() {
    char ch;
    while ((ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
      // count the number of lines
      if (ch == '\n')
        lineNumber++;
      tokenPos++;
    }
    return ch;
  }

  // return the line number of the last token got with getToken()
  public int getLineNumber() {
    return lineNumber;
  }

  public String getCurrentLine() {
    return getLine(tokenPos);
  }

  private String getLine(int index) {
    // get the line that contains input[index]. Assume input[index] is at a
    // token, not
    // a white space or newline

    int i;
    if (input.length <= 1)
      return "";
    i = index;
    if (i <= 0)
      i = 1;
    else if (i >= input.length)
      i = input.length;

    while (input[i] == '\n' || input[i] == '\r')
      i--;

    StringBuffer line = new StringBuffer();
    // go to the beginning of the line
    while (i >= 1 && input[i] != '\n')
      i--;
    if (input[i] == '\n')
      i++;
    // go to the end of the line putting it in variable line
    while (input[i] != '\0' && input[i] != '\n' && input[i] != '\r') {
      line.append(input[i]);
      i++;
    }
    return line.toString();
  }

  public String getStringValue() {
    return stringValue;
  }

  public int getNumberValue() {
    return numberValue;
  }

  public String getLiteralStringValue() {
    return literalStringValue;
  }
}