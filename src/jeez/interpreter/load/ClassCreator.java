package jeez.interpreter.load;

import jeez.lang.Attribute;
import jeez.lang.Clazz;
import jeez.lang.Method;
import jeez.lang.expression.AssignmentExpression;
import jeez.lang.expression.ExpressionList;
import jeez.lang.expression.IfExpression;
import jeez.lang.expression.IntegerExpression;
import jeez.lang.expression.LiteralBooleanExpression;
import jeez.lang.expression.LiteralStringExpression;
import jeez.lang.expression.MessageSend;
import jeez.lang.expression.NullExpression;
import jeez.lang.expression.PrintExpression;
import jeez.lang.expression.PrintlnExpression;
import jeez.lang.expression.ReturnExpression;
import jeez.lang.expression.SelfExpression;
import jeez.lang.expression.UnaryExpression;
import jeez.lang.expression.VariableDeclaration;
import jeez.lang.expression.VariableExpression;

@SuppressWarnings("unchecked")
public interface ClassCreator {

  public void startClassCreation(Clazz clazz);

  public void createAttribute(Attribute attr);
  
  public void createConstructor(Method method);

  public void createMethod(Method method);

  public Class finishClassCreation(Clazz clazz);

  public void generateAssignment(AssignmentExpression assignmentExpression);

  public void generateIf(IfExpression ifExpression);

  public void generateExpressionList(ExpressionList expressionList);

  public void generateInteger(IntegerExpression integerExpression);

  public void generateBoolean(LiteralBooleanExpression literalBooleanExpression);

  public void generateString(LiteralStringExpression literalStringExpression);

  public void generateMessageSend(MessageSend messageSend);

  public void generateNull(NullExpression nullExpression);

  public void generatePrint(PrintExpression printExpression);

  public void generatePrintln(PrintlnExpression printlnExpression);

  public void generateReturn(ReturnExpression returnExpression);

  public void generateSelf(SelfExpression selfExpression);

  public void generateUnaryExpression(UnaryExpression unaryExpression);

  public void generateVariableDeclaration(
      VariableDeclaration variableDeclaration);

  public void generateVariableExpression(VariableExpression variableExpression);
}
