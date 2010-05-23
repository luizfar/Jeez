package jeez.lang.expression;

import jeez.interpreter.lexer.Symbol;

public class BinaryOperator {

  private Symbol symbol;
  
  public BinaryOperator(Symbol symbol) {
    this.symbol = symbol;
  }
  
  public Symbol getSymbol() {
    return symbol;
  }
  
  public Object apply(Object o1, Object o2) {
    return null;
  }

  public String getMessage() {
    switch (symbol) {
      case PLUS: return new String("_add");
      
      case MINUS: return new String("_minus");
      
      case MULTIPLIER: return new String("_times");
      
      case EQUAL: return new String("equals");
      
      default: throw new RuntimeException("Unknown operator: " + symbol);
    }
  }
}
