package jeez.interpreter.lexer;

import static org.junit.Assert.*;
import jeez.interpreter.lexer.JeezLexer;
import jeez.interpreter.lexer.JeezLexerException;
import jeez.interpreter.lexer.Symbol;

import org.junit.Test;


public class LexerCommentsTests {

  private JeezLexer lexer;
  
  @Test
  public void skipSingleLineCommentInTheBeginning() throws Exception {
    char[] code = "//this is a comment\nidentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipSingleLineCommentInTheMiddle() throws Exception {
    char[] code = "identifier\n//this is a comment\nidentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipSingleLineCommentInTheEnd() throws Exception {
    char[] code = "identifier\n//this is a comment".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipSingleLineCommentAfterCode() throws Exception {
    char[] code = "identifier //this is a comment\nanotherIdentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipMultipleLineCommentWithOneLine() throws Exception {
    char[] code = "identifier /*this is a comment*/\nanotherIdentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipMultipleLineComment() throws Exception {
    char[] code = "/*this is a comment\nwithmultiplelines\n*/\nIdentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test
  public void skipMultipleLineCommentInTheEnd() throws Exception {
    char[] code = "identifier /*this is a multiple line comment\n*/".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
    assertEquals(Symbol.EOF, lexer.token);
  }
  
  @Test(expected = JeezLexerException.class)
  public void errorWhenMultipleLineCommentIsNotFinished() throws Exception {
    char[] code = "identifier /*this is a\nanotherIdentifier".toCharArray();
    lexer = new JeezLexer(code);
    lexer.nextToken();
    assertEquals(Symbol.IDENTIFIER, lexer.token);
    lexer.nextToken();
  }
}
