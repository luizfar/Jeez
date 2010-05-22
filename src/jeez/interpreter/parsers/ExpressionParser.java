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
import jeez.lang.Integer;


public class ExpressionParser {
  
  private JeezParser jeezParser;
  
  public ExpressionParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
  }
  
  public Expression parseExpression() {
    Expression expression = parseExpressionHelper();
    while (jeezParser.getToken() == Symbol.DOT || jeezParser.getToken() == Symbol.ASSIGN) {
      if (jeezParser.getToken() == Symbol.DOT) {
        expression = parseMessageSendExpression(expression);
      }
      if (jeezParser.getToken() == Symbol.ASSIGN) {
        expression = parseAssignmentExpression(expression);
      }
    }
    
    return expression;
  }
  
  private Expression parseExpressionHelper() {
    Expression expression = null;
    switch (jeezParser.getToken()) {
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
    jeezParser.expect(IF);
    IfExpression ifExpression = new IfExpression(parseExpression());
    
    ifExpression.setIfExpression(parseExpression());
    if (jeezParser.getToken() == ELSE) {
      jeezParser.nextToken();
      ifExpression.setElseExpression(parseExpression());
    }
    return ifExpression;
  }
  
  private Expression parsePrintExpression() {
    jeezParser.expect(PRINT);
    return new PrintExpression(parseExpression());
  }
  
  private Expression parsePrintlnExpressiont() {
    jeezParser.expect(PRINTLN);
    return new PrintlnExpression(parseExpression());
  }
  
  private Expression parseDynamicDeclaration() {
    jeezParser.expect(DEF);
    String variableName = jeezParser.parseIdentifier();
    VariableDeclaration expression = new VariableDeclaration(variableName);
    
    if (jeezParser.getToken() == ASSIGN) {
      jeezParser.nextToken();
      expression.setInitExpression(parseExpression());
    }
    
    return expression;
  }
  
  private Expression parseTypedDeclaration(String className) {
    String variableName = jeezParser.parseIdentifier();
    TypedVariableDeclaration expression = 
      new TypedVariableDeclaration(className, variableName);
    
    if (jeezParser.getToken() == ASSIGN) {
      jeezParser.nextToken();
      expression.setInitExpression(parseExpression());
    }
    
    return expression;
  }
  
  private Expression parseReturnExpression() {
    jeezParser.expect(RETURN);
    return new ReturnExpression(parseExpression());
  }
  
  private Expression parseExpressionList() {
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    ExpressionList expressions = new ExpressionList();
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      expressions.addToExpressions(parseExpression());
    }
    jeezParser.expect(RIGHT_CUR_BRACKET);
    
    return expressions;
  }
  
  private Expression parseAssignmentExpression(String variableName) {
    jeezParser.expect(ASSIGN);
    return new AssignmentExpression(variableName, parseExpression());
  }
  
  private Expression parseAssignmentExpression(Expression expression) {
    if (expression.getClass() != VariableExpression.class) {
      throw new RuntimeException("Can't assign to this");
    }
    return parseAssignmentExpression(((VariableExpression) expression).getVariableName());
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
        jeezParser.nextToken();
        return new NullExpression();
      
      case SELF:
        jeezParser.nextToken();
        return new SelfExpression();
        
      case LEFT_PAR:
        throw new RuntimeException("Not yet implemented.");
        
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
    
    throw new RuntimeException(jeezParser.getLineNumber() + ": Should throw an error here");
  }

  private Expression parseDeclarationOrVariableOrMethodExpression() {
    String identifier = jeezParser.getStringValue();
    jeezParser.nextToken();
    
    switch (jeezParser.getToken()) {
      case IDENTIFIER:
        if (jeezParser.foundEndOfExpression()) {
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
    jeezParser.expect(LEFT_PAR);
    while (jeezParser.getToken() != RIGHT_PAR) {
      messageSend.addToArguments(parseExpression());
      if (jeezParser.getToken() == COMMA) {
        jeezParser.nextToken();
      }
    }
    jeezParser.nextToken();
    
    return messageSend;
  }
  
  private Expression parseMessageSendExpression(Expression receiver) {
    jeezParser.expect(DOT);
    MessageSend messageSend = new MessageSend(receiver, jeezParser.parseIdentifier());
    jeezParser.expect(LEFT_PAR);
    while (jeezParser.getToken() != RIGHT_PAR) {
      messageSend.addToArguments(parseExpression());
      if (jeezParser.getToken() == COMMA) {
        jeezParser.nextToken();
      }
    }
    jeezParser.nextToken();
    
    return messageSend;
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
    
    return new IntegerExpression(new Integer(value));
  }
  
  private Expression parseLiteralStringExpression() {
    String stringLiteral = jeezParser.getStringLiteral();
    jeezParser.nextToken();
    
    return new LiteralStringExpression(stringLiteral);
  }
}
