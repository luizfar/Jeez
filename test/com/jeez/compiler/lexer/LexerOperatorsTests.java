package com.jeez.compiler.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerOperatorsTests {

  private Lexer lexer;
  
  @Test
  public void plus() throws Exception {
    char[] code = "a + b".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.PLUS, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void greaterThan() throws Exception {
    char[] code = "a > b".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.GREATER_THAN, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void greaterOrEquals() throws Exception {
    char[] code = "a >= b".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.GREATER_EQUAL, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void notEquals() throws Exception {
    char[] code = "a != b".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.NOT_EQUAL, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void equals() throws Exception {
    char[] code = "a==b".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EQUAL, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void commaAndBrackets() throws Exception {
    char[] code = "a() { [b, c] }".toCharArray();
    lexer = new Lexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.LEFT_PAR, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.RIGHT_PAR, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.LEFT_CUR_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.LEFT_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.COMMA, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.RIGHT_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.RIGHT_CUR_BRACKET, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
}
