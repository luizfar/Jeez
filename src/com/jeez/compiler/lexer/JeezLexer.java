package com.jeez.compiler.lexer;

import java.util.Arrays;
import java.util.Hashtable;

public class JeezLexer {

  public Symbol token;
  private String stringValue, literalStringValue;
  private int numberValue;

  private int  tokenPos;
  
  private int lineNumber;
  
  private char []input;
  
  private static final Hashtable<String, Symbol> KEYWORDS = new Hashtable<String, Symbol>();
  
  private static final Hashtable<String, Symbol> OPERATORS = new Hashtable<String, Symbol>();
  
  static {
    KEYWORDS.put("true", Symbol.TRUE);
    KEYWORDS.put("false", Symbol.FALSE);
    KEYWORDS.put("static", Symbol.STATIC);
    KEYWORDS.put("void", Symbol.VOID);
    KEYWORDS.put("null", Symbol.NULL);
    KEYWORDS.put("if", Symbol.IF);
    KEYWORDS.put("else", Symbol.ELSE);
    KEYWORDS.put("while", Symbol.WHILE);
    KEYWORDS.put("read", Symbol.READ);
    KEYWORDS.put("write", Symbol.WRITE);
    KEYWORDS.put("break", Symbol.BREAK);
    KEYWORDS.put("int", Symbol.INT);
    KEYWORDS.put("boolean", Symbol.BOOLEAN);
    KEYWORDS.put("return", Symbol.RETURN);
    KEYWORDS.put("class", Symbol.CLASS);
    KEYWORDS.put("super", Symbol.SUPER);
    KEYWORDS.put("this", Symbol.THIS);
    KEYWORDS.put("new", Symbol.NEW);
    KEYWORDS.put("public", Symbol.PUBLIC);
    KEYWORDS.put("protected", Symbol.PROTECTED);
    KEYWORDS.put("private", Symbol.PRIVATE);
    KEYWORDS.put("extends", Symbol.EXTENDS);
    KEYWORDS.put("abstract", Symbol.ABSTRACT);
    
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
    OPERATORS.put("(", Symbol.LEFT_PAR);
    OPERATORS.put(")", Symbol.RIGHT_PAR);
    OPERATORS.put("{", Symbol.LEFT_CUR_BRACKET);
    OPERATORS.put("}", Symbol.RIGHT_CUR_BRACKET);
    OPERATORS.put("[", Symbol.LEFT_BRACKET);
    OPERATORS.put("]", Symbol.RIGHT_BRACKET);
    OPERATORS.put(",", Symbol.COMMA);
    OPERATORS.put(";", Symbol.SEMICOLON);
    OPERATORS.put(".", Symbol.DOT);
    OPERATORS.put("&&", Symbol.AND);
    OPERATORS.put("||", Symbol.OR);
  }
  
  public JeezLexer(char[] originalInput) {
    input = Arrays.copyOf(originalInput, originalInput.length + 1);
    input[input.length - 1] = '\0';
    
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
        parseKeywordOrIdentifier();
      } else if (isDigit(ch)) {
        parseLiteralNumber();
      } else if (ch == '"') {
        parseLiteralString();
      } else {
        parseOperator();
      }
    }
  }

  private void parseKeywordOrIdentifier() {
    StringBuffer identifier = new StringBuffer();
    while (isLetterOrDigit(input[tokenPos])) {
      identifier.append(input[tokenPos]);
      tokenPos++;
    }
    
    stringValue = identifier.toString();
    token = KEYWORDS.get(stringValue);
    if (token == null) {
      token = Symbol.IDENTIFIER;
    }
  }

  private void parseLiteralNumber() {
    StringBuffer number = new StringBuffer();
    while (isDigit(input[tokenPos])) {
      number.append(input[tokenPos]);
      tokenPos++;
    }
    
    try {
      numberValue = Integer.valueOf(number.toString()).intValue();
    } catch (NumberFormatException e) {
      throw new JeezLexerException("Number out of limits", lineNumber);
    }
    
    token = Symbol.NUMBER;
  }

  private void parseLiteralString() {
    tokenPos++;
    StringBuffer s = new StringBuffer();
    while (input[tokenPos] != '\0' && input[tokenPos] != '\n' && input[tokenPos] != '"') {
      if (input[tokenPos] == '\\') {
        if (input[tokenPos + 1] != '\n' && input[tokenPos + 1] != '\0') {
          s.append(input[tokenPos]);
          tokenPos++;
          s.append(input[tokenPos]);
          tokenPos++;
        }
      } else {
        s.append(input[tokenPos]);
        tokenPos++;
      }
    }
    
    if (input[tokenPos] == '"') {
      tokenPos++;
      literalStringValue = s.toString();
    } else {
      literalStringValue = "";
      throw new JeezLexerException("Unterminated string literal", lineNumber);
    }
    
    token = Symbol.LITERAL_STRING;
  }

  private void parseOperator() {
    String operator = String.valueOf(input[tokenPos]) + input[tokenPos + 1];
    token = OPERATORS.get(operator);
    if (token != null) {
      tokenPos += 2;
    } else {
      token = OPERATORS.get(String.valueOf(input[tokenPos]));
      tokenPos++;
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
    ch = input[tokenPos];
    while (ch != '\0' && !(ch == '*' && input[tokenPos + 1] == '/')) {
      if (ch == '\n') {
        lineNumber++;
      }
      tokenPos++;      
      ch = input[tokenPos];
    }
    
    if (ch == '*') {
      tokenPos += 2;
    } else {
      throw new JeezLexerException("Comment opened and not closed", lineNumberStartComment);
    }
  }

  private void skipSingleLineComment() {
    while (input[tokenPos] != '\0' && input[tokenPos] != '\n')
      tokenPos++;
  }

  private char findNextChar() {
    char ch = input[tokenPos];
    while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
      if (ch == '\n') {
        lineNumber++;
      }
      tokenPos++;
      ch = input[tokenPos];
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
  
  public char[] getInput() {
    return input;
  }
}