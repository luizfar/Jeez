package com.jeez.compiler.lexer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LexerLiteralsTests {
  
  private Lexer lexer;
  
  @Test
  public void literalString() throws Exception {
    char[] code = "a = \"a string\"".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.LITERAL_STRING, lexer.token);
    assertEquals("a string", lexer.getLiteralStringValue());
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void literalStringWithEscapedCharacters() throws Exception {
    char[] code = "a = \"a \\ns\\t\\string\"".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.LITERAL_STRING, lexer.token);
    assertEquals("a \\ns\\t\\string", lexer.getLiteralStringValue());
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test(expected = LexerException.class)
  public void unterminatedLiteralString() throws Exception {
    char[] code = "a = \"a string\n\"".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.LITERAL_STRING, lexer.token);
  }
  
  @Test
  public void literalNumber() throws Exception {
    char[] code = "a = 123".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.NUMBER, lexer.token);
    assertEquals(123, lexer.getNumberValue());
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
}
