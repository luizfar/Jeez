package jeez.lang.expression;

import static com.jeez.compiler.lexer.Symbol.EQUAL;
import static com.jeez.compiler.lexer.Symbol.PLUS;
import jeez.lang.context.ExecutionContext;
import jeez.lang.java.JeezNumber;

public class BinaryExpression implements Expression {

  private BinaryOperator operator;
  
  private Expression leftSide;
  
  private Expression rightSide;
  
  public BinaryExpression(BinaryOperator operator, Expression leftSide, Expression rightSide) {
    this.operator = operator;
    this.leftSide = leftSide;
    this.rightSide = rightSide;
  }
  
  public BinaryOperator getOperator() {
    return operator;
  }

  public Expression getLeftSide() {
    return leftSide;
  }

  public Expression getRightSide() {
    return rightSide;
  }

  @Override
  public Object evaluate(ExecutionContext context) {
    if (operator.getSymbol() == EQUAL) {
      return leftSide.evaluate(context).equals(rightSide.evaluate(context));
    }
    if (operator.getSymbol() == PLUS) {
      Object l1 = leftSide.evaluate(context);
      JeezNumber left = (JeezNumber) l1;
      JeezNumber right = (JeezNumber) rightSide.evaluate(context);
      return left.add(right);
    }
    
    return null;
  }
}
