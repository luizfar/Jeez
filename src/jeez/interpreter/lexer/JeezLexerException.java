package jeez.interpreter.lexer;

public class JeezLexerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JeezLexerException(String message, int lineNumber) {
    super("Error at line " + lineNumber + ": " + message);
  }
}
