package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.AND;
import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.DIVISOR;
import static com.jeez.compiler.lexer.Symbol.DOT;
import static com.jeez.compiler.lexer.Symbol.EQUAL;
import static com.jeez.compiler.lexer.Symbol.GREATER_EQUAL;
import static com.jeez.compiler.lexer.Symbol.GREATER_THAN;
import static com.jeez.compiler.lexer.Symbol.IS;
import static com.jeez.compiler.lexer.Symbol.LEFT_BRACKET;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.LESS_EQUAL;
import static com.jeez.compiler.lexer.Symbol.LESS_THAN;
import static com.jeez.compiler.lexer.Symbol.MINUS;
import static com.jeez.compiler.lexer.Symbol.MULTIPLIER;
import static com.jeez.compiler.lexer.Symbol.NOT;
import static com.jeez.compiler.lexer.Symbol.NOT_EQUAL;
import static com.jeez.compiler.lexer.Symbol.OR;
import static com.jeez.compiler.lexer.Symbol.PLUS;
import static com.jeez.compiler.lexer.Symbol.REMAINDER;
import static com.jeez.compiler.lexer.Symbol.RIGHT_PAR;
import static com.jeez.compiler.lexer.Symbol.SHORT_AND;
import static com.jeez.compiler.lexer.Symbol.SHORT_OR;
import static com.jeez.compiler.lexer.Symbol.TRUE;
import static com.jeez.compiler.lexer.Symbol.XOR;
import jeez.lang.expression.BinaryExpression;
import jeez.lang.expression.BinaryOperator;
import jeez.lang.expression.Expression;
import jeez.lang.expression.IntegerExpression;
import jeez.lang.expression.LiteralBooleanExpression;
import jeez.lang.expression.LiteralStringExpression;
import jeez.lang.expression.MessageSendExpression;
import jeez.lang.expression.NullExpression;
import jeez.lang.expression.UnaryExpression;
import jeez.lang.expression.VariableExpression;
import jeez.lang.java.JeezInteger;
import jeez.lang.statement.MessageSend;

import com.jeez.compiler.lexer.Symbol;

public class ExpressionParser {

  private JeezParser jeezParser;
  
  public ExpressionParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
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

  private Expression parseVariableOrMethodExpression() {
    String identifier = jeezParser.getStringValue();
    jeezParser.nextToken();
    
    Expression expression;
    
    if (jeezParser.getToken() == LEFT_PAR) {
      expression = parseMessageSendToThisExpression(identifier);
    } else if (jeezParser.getToken() == DOT) {
      expression = parseMessageSendExpression(identifier);
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
  
  private Expression parseMessageSendExpression(String receiver) {
    jeezParser.expect(DOT);
    // TODO luiz extract message send parser for statement and expression
    MessageSend messageSend = new MessageSend(receiver, jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_PAR);
    while (jeezParser.getToken() != RIGHT_PAR) {
      messageSend.addToArguments(parseExpression());
      if (jeezParser.getToken() == COMMA) {
        jeezParser.nextToken();
      }
    }
    jeezParser.nextToken();
    
    return new MessageSendExpression(messageSend);
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
