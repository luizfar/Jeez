package jeez.lang;

public class UnknownMessageException extends RuntimeException {

  private static final long serialVersionUID = -1943413456447278510L;

  public UnknownMessageException(String message) {
    super(message);
  }
}
