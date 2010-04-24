package com.jeez.compiler.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTests {
  
  private JeezLexer lexer;
  
  @Test
  public void keywordAndIdentifier() throws Exception {
    char[] code = "class someclass { }".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.CLASS, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    assertEquals("someclass", lexer.getStringValue());
    lexer.nextToken();
    assertEquals(Symbol.LEFT_CUR_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.RIGHT_CUR_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void literalString() throws Exception {
    char[] code = "a = \"a string\"".toCharArray();
    lexer = new JeezLexer(code);
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
    lexer = new JeezLexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.LITERAL_STRING, lexer.token);
    assertEquals("a \\ns\\t\\string", lexer.getLiteralStringValue());
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test(expected = JeezLexerException.class)
  public void unterminatedLiteralString() throws Exception {
    char[] code = "a = \"a string\n\"".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.LITERAL_STRING, lexer.token);
  }
  
  @Test
  public void literalNumber() throws Exception {
    char[] code = "a = 123".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    assertEquals(Symbol.NUMBER, lexer.token);
    assertEquals(123, lexer.getNumberValue());
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
}
