package com.jeez.compiler.lexer;

public enum Symbol {
  
  EOF,
  IDENTIFIER,
  NUMBER,
  PLUS,
  MINUS,
  MULTIPLIER,
  REMAINDER,
  DIVISOR,
  TRUE,
  FALSE,
  FINAL,
  VOID,
  NULL,
  LESS_THAN,
  LESS_EQUAL,
  GREATER_THAN,
  GREATER_EQUAL,
  NOT_EQUAL,
  EQUAL,
  ASSIGN,
  LEFT_PAR,
  RIGHT_PAR,
  SEMICOLON,
  COLON,
  DOT,
  IF,
  ELSE,
  WHILE,
  READ,
  WRITE,
  BREAK,
  INT,
  BOOLEAN,
  RETURN,
  CLASS,
  EXTENDS,
  IMPLEMENTS,
  SUPER,
  THIS,
  NEW,
  PUBLIC,
  PROTECTED,
  PRIVATE,
  COMMA,
  LITERAL_STRING,
  AND,
  OR,
  LEFT_BRACKET,
  RIGHT_BRACKET,
  LEFT_CUR_BRACKET,
  RIGHT_CUR_BRACKET,
  NOT,
  STATIC,
  LastSymbol;
}