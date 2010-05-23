package jeez.interpreter.lexer;

public enum Symbol {
  
  EOF,
  IDENTIFIER,
  INTEGER,
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
  RETURN,
  CLASS,
  EXTENDS,
  IMPLEMENTS,
  SUPER,
  SELF,
  PUBLIC,
  PROTECTED,
  PRIVATE,
  COMMA,
  LITERAL_STRING,
  AND,
  OR,
  XOR,
  IS,
  SHORT_AND,
  SHORT_OR,
  LEFT_BRACKET,
  RIGHT_BRACKET,
  LEFT_CUR_BRACKET,
  RIGHT_CUR_BRACKET,
  NOT,
  STATIC,
  ABSTRACT,
  PRINT,
  PRINTLN,
  DEF,
  MODULE,
  LastSymbol;
}