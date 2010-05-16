package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;
import jeez.lang.Method;
import jeez.lang.expression.BinaryExpression;
import jeez.lang.expression.BinaryOperator;
import jeez.lang.expression.Expression;
import jeez.lang.expression.InstantiationExpression;
import jeez.lang.expression.IntegerExpression;
import jeez.lang.expression.LiteralBooleanExpression;
import jeez.lang.expression.LiteralStringExpression;
import jeez.lang.expression.NullExpression;
import jeez.lang.expression.UnaryExpression;
import jeez.lang.expression.VariableExpression;
import jeez.lang.java.JeezInteger;

import com.jeez.compiler.lexer.Symbol;

public class JeezExpressionParser {

  private JeezParser jeezParser;
  
  public JeezExpressionParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
  }
  
  public void setCurrentFunction(Method currentMethod) {
    
  }
  
  public Expression parseExpression() {
    return parseOrExpression();
  }

  private Expression parseOrExpression() {
    Expression expr = parseAndExpression();
    
    while (jeezParser.getToken() == OR) {
      jeezParser.nextToken();
      Expression right = parseAndExpression();
      expr = new BinaryExpression(new BinaryOperator(OR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseAndExpression() {
    Expression expr = parseShortOrExpression();
    
    while (jeezParser.getToken() == AND) {
      jeezParser.nextToken();
      Expression right = parseShortOrExpression();
      expr = new BinaryExpression(new BinaryOperator(AND), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseShortOrExpression() {
    Expression expr = parseXorExpression();
    
    while (jeezParser.getToken() == SHORT_OR) {
      jeezParser.nextToken();
      Expression right = parseXorExpression();
      expr = new BinaryExpression(new BinaryOperator(SHORT_OR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseXorExpression() {
    Expression expr = parseShortAndExpression();
    
    while (jeezParser.getToken() == XOR) {
      jeezParser.nextToken();
      Expression right = parseShortAndExpression();
      expr = new BinaryExpression(new BinaryOperator(XOR), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseShortAndExpression() {
    Expression expr = parseEqualExpression();
    
    while (jeezParser.getToken() == SHORT_AND) {
      jeezParser.nextToken();
      Expression right = parseEqualExpression();
      expr = new BinaryExpression(new BinaryOperator(SHORT_AND), expr, right);
    }
    
    return expr;
  }
  
  private Expression parseEqualExpression() {
    Expression expr = parseRelationalExpression();
    
    while (jeezParser.getToken() == EQUAL || jeezParser.getToken() == NOT_EQUAL) {
      BinaryOperator operator = new BinaryOperator(jeezParser.getToken());
      jeezParser.nextToken();
      Expression right = parseRelationalExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }
  
  private Expression parseRelationalExpression() {
    Expression expr = parseAddExpression();
    
    while (jeezParser.getToken() == GREATER_THAN ||
           jeezParser.getToken() == GREATER_EQUAL ||
           jeezParser.getToken() == LESS_THAN ||
           jeezParser.getToken() == LESS_EQUAL ||
           jeezParser.getToken() == IS) {
      BinaryOperator operator = new BinaryOperator(jeezParser.getToken());
      jeezParser.nextToken();
      Expression right = parseAddExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseAddExpression() {
    Expression expr = parseMultiplyExpression();
    
    while (jeezParser.getToken() == PLUS || jeezParser.getToken() == MINUS) {
      BinaryOperator operator = new BinaryOperator(jeezParser.getToken());
      jeezParser.nextToken();
      
      Expression right = parseMultiplyExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseMultiplyExpression() {
    Expression expr = parseUnaryExpression();
    
    while (jeezParser.getToken() == MULTIPLIER
        || jeezParser.getToken() == DIVISOR
        || jeezParser.getToken() == REMAINDER) {
      BinaryOperator operator = new BinaryOperator(jeezParser.getToken());
      jeezParser.nextToken();
      
      Expression right = parseUnaryExpression();
      expr = new BinaryExpression(operator, expr, right);
    }
    
    return expr;
  }

  private Expression parseUnaryExpression() {
    if (jeezParser.getToken() == PLUS || jeezParser.getToken() == MINUS
        || jeezParser.getToken() == NOT) {
      Symbol operator = jeezParser.getToken();
      jeezParser.nextToken();
      
      return new UnaryExpression(operator, parsePrimaryExpression());
    }
    return parsePrimaryExpression();
  }

  private Expression parsePrimaryExpression() {
    switch (jeezParser.getToken()) {
      case NULL:
        return new NullExpression();
      
      case THIS:
      case LEFT_PAR:
        throw new RuntimeException("Not yet implemented.");
        
      case NEW:
        return parseInstantiationExpression();
        
      case LEFT_BRACKET:
        throw new RuntimeException("Not yet implemented.");
      
      case IDENTIFIER:
        return parseVariableOrMethodExpression();
      
      case TRUE:
      case FALSE:
        return parseLiteralBooleanExpression();
        
      case INTEGER:
        return parseIntegerExpression();
      
      case LITERAL_STRING:
        return parseLiteralStringExpression();
    }
    
    throw new RuntimeException("Should throw an error here");
  }
  
  private Expression parseInstantiationExpression() {
    jeezParser.expect(NEW);
    String clazzName = jeezParser.parseIdentifier();
    jeezParser.expect(LEFT_PAR);
    jeezParser.expect(RIGHT_PAR);
    
    return new InstantiationExpression(clazzName);
  }

  private Expression parseVariableOrMethodExpression() {
    String identifier = jeezParser.getStringValue();
    jeezParser.nextToken();
    
    Expression expression;
    
    if (jeezParser.getToken() == LEFT_PAR) {
      expression = parseMessageSendToThisExpression(identifier);
    } else {
      expression = parseVariableExpression(identifier);
    }
    
    if (jeezParser.getToken() == LEFT_BRACKET) {
//      expression = parseListOrMapIndexExpression();
      throw new RuntimeException("Not yet implemented.");
    }
    
    return expression;
  }
  
  private Expression parseMessageSendToThisExpression(String messageName) {    
    return null;
  }
  
  private Expression parseVariableExpression(String identifier) {
    return new VariableExpression(identifier);
  }
  
  private Expression parseLiteralBooleanExpression() {
    boolean value = jeezParser.getToken() == TRUE;
    jeezParser.nextToken();
    
    return new LiteralBooleanExpression(value);
  }
  
  private Expression parseIntegerExpression() {
    int value = jeezParser.getIntegerValue();
    jeezParser.nextToken();
    
    return new IntegerExpression(new JeezInteger(value));
  }
  
  private Expression parseLiteralStringExpression() {
    String stringLiteral = jeezParser.getStringLiteral();
    jeezParser.nextToken();
    
    return new LiteralStringExpression(stringLiteral);
  }
}
