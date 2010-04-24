package com.jeez.compiler.lexer;

public class LexerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LexerException(String message, int lineNumber) {
    super("Error at line " + lineNumber + ": " + message);
  }
}
