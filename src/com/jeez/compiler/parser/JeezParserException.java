package com.jeez.compiler.parser;

public class JeezParserException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JeezParserException(String message, int line) {
    super("Parser error on line " + line + ": " + message);
  }
}
