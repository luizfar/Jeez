package jeez.lang.expression;


public class BinaryExpression extends MessageSend implements Expression {

  private BinaryOperator operator;
  
  public BinaryExpression(BinaryOperator operator, Expression leftSide, Expression rightSide) {
    super(leftSide, operator.getMessage());
    addToArguments(rightSide);
    this.operator = operator;
  }
  
  public BinaryOperator getOperator() {
    return operator;
  }
}
