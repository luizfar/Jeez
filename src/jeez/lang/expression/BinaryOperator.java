package jeez.lang.expression;

import com.jeez.compiler.lexer.Symbol;

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
}
