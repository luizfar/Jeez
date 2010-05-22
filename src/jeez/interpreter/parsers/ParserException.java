package jeez.interpreter.parsers;

public class ParserException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ParserException(String message, int line) {
    super("Parser error on line " + line + ": " + message);
  }
}
