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
      case PLUS: return "_add";
      
      case MINUS: return "_minus";
      
      case MULTIPLIER: return "_times";
      
      case EQUAL: return "equals";
      
      default: throw new RuntimeException("Unknown operator: " + symbol);
    }
  }
}
