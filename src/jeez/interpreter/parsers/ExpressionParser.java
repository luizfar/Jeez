package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.AND;
import static jeez.interpreter.lexer.Symbol.ASSIGN;
import static jeez.interpreter.lexer.Symbol.COMMA;
import static jeez.interpreter.lexer.Symbol.DEF;
import static jeez.interpreter.lexer.Symbol.DIVISOR;
import static jeez.interpreter.lexer.Symbol.DOT;
import static jeez.interpreter.lexer.Symbol.ELSE;
import static jeez.interpreter.lexer.Symbol.EQUAL;
import static jeez.interpreter.lexer.Symbol.GREATER_EQUAL;
import static jeez.interpreter.lexer.Symbol.GREATER_THAN;
import static jeez.interpreter.lexer.Symbol.IF;
import static jeez.interpreter.lexer.Symbol.IS;
import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.LEFT_PAR;
import static jeez.interpreter.lexer.Symbol.LESS_EQUAL;
import static jeez.interpreter.lexer.Symbol.LESS_THAN;
import static jeez.interpreter.lexer.Symbol.MINUS;
import static jeez.interpreter.lexer.Symbol.MULTIPLIER;
import static jeez.interpreter.lexer.Symbol.NOT;
import static jeez.interpreter.lexer.Symbol.NOT_EQUAL;
import static jeez.interpreter.lexer.Symbol.OR;
import static jeez.interpreter.lexer.Symbol.PLUS;
import static jeez.interpreter.lexer.Symbol.PRINT;
import static jeez.interpreter.lexer.Symbol.PRINTLN;
import static jeez.interpreter.lexer.Symbol.REMAINDER;
import static jeez.interpreter.lexer.Symbol.RETURN;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.RIGHT_PAR;
import static jeez.interpreter.lexer.Symbol.SHORT_AND;
import static jeez.interpreter.lexer.Symbol.SHORT_OR;
import static jeez.interpreter.lexer.Symbol.TRUE;
import static jeez.interpreter.lexer.Symbol.XOR;
import jeez.interpreter.lexer.Symbol;
import jeez.lang.expression.AssignmentExpression;
import jeez.lang.expression.BinaryExpression;
import jeez.lang.expression.BinaryOperator;
import jeez.lang.expression.Expression;
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
import jeez.lang.expression.TypedVariableDeclaration;
import jeez.lang.expression.UnaryExpression;
import jeez.lang.expression.VariableDeclaration;
import jeez.lang.expression.VariableExpression;
import jeez.lang.JeezInteger;

public class ExpressionParser {
  
  private MainParser mainParser;
  
  public ExpressionParser(MainParser jeezParser) {
    this.mainParser = jeezParser;
  }
  
  public Expression parseExpression() {
    Expression expression = parseExpressionHelper();
    while (mainParser.getToken() == Symbol.DOT || mainParser.getToken() == Symbol.ASSIGN) {
      if (mainParser.getToken() == Symbol.DOT) {
        expression = parseMessageSendExpression(expression);
      }
      if (mainParser.getToken() == Symbol.ASSIGN) {
        expression = parseAssignmentExpression(expression);
      }
    }
    
    return expression;
  }
  
  private Expression parseExpressionHelper() {
    Expression expression = null;
    switch (mainParser.getToken()) {
      case IF:
        expression = parseIfExpression();
        break;
        
      case PRINT:
        expression = parsePrintExpression();
        break;
        
      case PRINTLN:
        expression = parsePrintlnExpressiont();
        break;
        
      case DEF:
        expression = parseDynamicDeclaration();
        break;
        
      case RETURN:
        expression = parseReturnExpression();
        break;
        
      case LEFT_CUR_BRACKET:
        expression = parseExpressionList();
        break;
    }
    
    if (expression == null) {
      expression = parseOrExpression();
    }
    
    return expression;
  }
  
  private Expression parseIfExpression() {
    mainParser.expect(IF);
    
    boolean parenthesisUsed = false;
    if (mainParser.getToken() == LEFT_PAR) {
      mainParser.nextToken();
      parenthesisUsed = true;
    }
    
    IfExpression ifExpression = new IfExpression(parseExpression());
    
    if (parenthesisUsed) {
      mainParser.expect(RIGHT_PAR);
    }
    
    ifExpression.setIfExpression(parseExpression());
    if (mainParser.getToken() == ELSE) {
      mainParser.nextToken();
      ifExpression.setElseExpression(parseExpression());
    }
    return ifExpression;
  }
  
  private Expression parsePrintExpression() {
    mainParser.expect(PRINT);
    return new PrintExpression(parseExpression());
  }
  
  private Expression parsePrintlnExpressiont() {
    mainParser.expect(PRINTLN);
    return new PrintlnExpression(parseExpression());
  }
  
  private Expression parseDynamicDeclaration() {
    mainParser.expect(DEF);
    String variableName = mainParser.parseIdentifier();
    VariableDeclaration expression = new VariableDeclaration(variableName);
    
    if (mainParser.getToken() == ASSIGN) {
      mainParser.nextToken();
      expression.setInitExpression(parseExpression());
    }
    
    return expression;
  }
  
  private Expression parseTypedDeclaration(String className) {
    String variableName = mainParser.parseIdentifier();
    TypedVariableDeclaration expression = 
      new TypedVariableDeclaration(className, variableName);
    
    if (mainParser.getToken() == ASSIGN) {
      mainParser.nextToken();
      expression.setInitExpression(parseExpression());
    }
    
    return expression;
  }
  
  private Expression parseReturnExpression() {
    mainParser.expect(RETURN);
    return new ReturnExpression(parseExpression());
  }
  
  private Expression parseExpressionList() {
    mainParser.expect(LEFT_CUR_BRACKET);
    
    ExpressionList expressions = new ExpressionList();
    while (mainParser.getToken() != RIGHT_CUR_BRACKET) {
      expressions.addToExpressions(parseExpression());
    }
    mainParser.expect(RIGHT_CUR_BRACKET);
    
    return expressions;
  }
  
  private Expression parseAssignmentExpression(String variableName) {
    mainParser.expect(ASSIGN);
    return new AssignmentExpression(variableName, parseExpression());
  }
  
  private Expression parseAssignmentExpression(Expression expression) {
    if (expression.getClass() != VariableExpression.class) {
      throw new RuntimeException("Can't assign to this");
    }
    return parseAssignmentExpression(((VariableExpression) expression)
        .getVariableName());
  }

  private Expression parseOrExpression() {
    Expression expr = parseAndExpression();
    
    while (mainParser.getToken() == OR) {
      mainParser.nextToken();
      Expression right = parseAndExpression();
      expr = new BinaryExpression(new BinaryOperator(OR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseAndExpression() {
    Expression expr = parseShortOrExpression();
    
    while (mainParser.getToken() == AND) {
      mainParser.nextToken();
      Expression right = parseShortOrExpression();
      expr = new BinaryExpression(new BinaryOperator(AND), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseShortOrExpression() {
    Expression expr = parseXorExpression();
    
    while (mainParser.getToken() == SHORT_OR) {
      mainParser.nextToken();
      Expression right = parseXorExpression();
      expr = new BinaryExpression(new BinaryOperator(SHORT_OR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseXorExpression() {
    Expression expr = parseShortAndExpression();
    
    while (mainParser.getToken() == XOR) {
      mainParser.nextToken();
      Expression right = parseShortAndExpression();
      expr = new BinaryExpression(new BinaryOperator(XOR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseShortAndExpression() {
    Expression expr = parseEqualExpression();
    
    while (mainParser.getToken() == SHORT_AND) {
      mainParser.nextToken();
      Expression right = parseEqualExpression();
      expr = new BinaryExpression(new BinaryOperator(SHORT_AND), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseEqualExpression() {
    Expression expr = parseRelationalExpression();
    
    while (mainParser.getToken() == EQUAL || mainParser.getToken() == NOT_EQUAL) {
      BinaryOperator operator = new BinaryOperator(mainParser.getToken());
      mainParser.nextToken();
      Expression right = parseRelationalExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }
  
  private Expression parseRelationalExpression() {
    Expression expr = parseAddExpression();
    
    while (mainParser.getToken() == GREATER_THAN ||
           mainParser.getToken() == GREATER_EQUAL ||
           mainParser.getToken() == LESS_THAN ||
           mainParser.getToken() == LESS_EQUAL ||
           mainParser.getToken() == IS) {
      BinaryOperator operator = new BinaryOperator(mainParser.getToken());
      mainParser.nextToken();
      Expression right = parseAddExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseAddExpression() {
    Expression expr = parseMultiplyExpression();
    
    while (mainParser.getToken() == PLUS || mainParser.getToken() == MINUS) {
      BinaryOperator operator = new BinaryOperator(mainParser.getToken());
      mainParser.nextToken();
      
      Expression right = parseMultiplyExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseMultiplyExpression() {
    Expression expr = parseUnaryExpression();
    
    while (mainParser.getToken() == MULTIPLIER
        || mainParser.getToken() == DIVISOR
        || mainParser.getToken() == REMAINDER) {
      BinaryOperator operator = new BinaryOperator(mainParser.getToken());
      mainParser.nextToken();
      
      Expression right = parseUnaryExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseUnaryExpression() {
    if (mainParser.getToken() == PLUS || mainParser.getToken() == MINUS
        || mainParser.getToken() == NOT) {
      Symbol operator = mainParser.getToken();
      mainParser.nextToken();
      
      return new UnaryExpression(operator, parsePrimaryExpression());
    }
    return parsePrimaryExpression();
  }

  private Expression parsePrimaryExpression() {
    switch (mainParser.getToken()) {
      case NULL:
        mainParser.nextToken();
        return new NullExpression();
      
      case SELF:
        mainParser.nextToken();
        return new SelfExpression();
        
      case LEFT_PAR:
        throw new RuntimeException(mainParser.getLineNumber() + ": Not yet implemented.");
        
      case LEFT_BRACKET:
        throw new RuntimeException("Not yet implemented.");
      
      case IDENTIFIER:
        return parseDeclarationOrVariableOrMethodExpression();
      
      case TRUE:
      case FALSE:
        return parseLiteralBooleanExpression();
        
      case INTEGER:
        return parseIntegerExpression();
      
      case LITERAL_STRING:
        return parseLiteralStringExpression();
    }
    
    throw new RuntimeException(mainParser.getLineNumber() + ": Should throw an error here");
  }

  private Expression parseDeclarationOrVariableOrMethodExpression() {
    String identifier = mainParser.getStringValue();
    mainParser.nextToken();
    
    switch (mainParser.getToken()) {
      case IDENTIFIER:
        if (mainParser.foundEndOfExpression()) {
          return parseVariableExpression(identifier);
        }
        return parseTypedDeclaration(identifier);
        
      case LEFT_PAR:
        return parseMessageSendToThis(identifier);
        
      default:
        return parseVariableExpression(identifier);
    }
  }
  
  private Expression parseMessageSendToThis(String messageName) {
    MessageSend messageSend = new MessageSend(new SelfExpression(), messageName);
    mainParser.expect(LEFT_PAR);
    while (mainParser.getToken() != RIGHT_PAR) {
      messageSend.addToArguments(parseExpression());
      if (mainParser.getToken() == COMMA) {
        mainParser.nextToken();
      }
    }
    mainParser.nextToken();
    
    return messageSend;
  }
  
  private Expression parseMessageSendExpression(Expression receiver) {
    mainParser.expect(DOT);
    MessageSend messageSend = new MessageSend(receiver, mainParser.parseIdentifier());
    mainParser.expect(LEFT_PAR);
    while (mainParser.getToken() != RIGHT_PAR) {
      messageSend.addToArguments(parseExpression());
      if (mainParser.getToken() == COMMA) {
        mainParser.nextToken();
      }
    }
    mainParser.nextToken();
    
    return messageSend;
  }
  
  private Expression parseVariableExpression(String identifier) {
    return new VariableExpression(identifier);
  }
  
  private Expression parseLiteralBooleanExpression() {
    boolean value = mainParser.getToken() == TRUE;
    mainParser.nextToken();
    
    return new LiteralBooleanExpression(value);
  }
  
  private Expression parseIntegerExpression() {
    int value = mainParser.getIntegerValue();
    mainParser.nextToken();
    
    return new IntegerExpression(new JeezInteger.Integer(value));
  }
  
  private Expression parseLiteralStringExpression() {
    String stringLiteral = mainParser.getStringLiteral();
    mainParser.nextToken();
    
    return new LiteralStringExpression(stringLiteral);
  }
}
