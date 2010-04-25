package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.AND;
import static com.jeez.compiler.lexer.Symbol.DIVISOR;
import static com.jeez.compiler.lexer.Symbol.MINUS;
import static com.jeez.compiler.lexer.Symbol.MULTIPLIER;
import static com.jeez.compiler.lexer.Symbol.NOT;
import static com.jeez.compiler.lexer.Symbol.OR;
import static com.jeez.compiler.lexer.Symbol.PLUS;
import static com.jeez.compiler.lexer.Symbol.REMAINDER;

import com.jeez.compiler.ast.expr.AddExpression;
import com.jeez.compiler.ast.expr.AndExpression;
import com.jeez.compiler.ast.expr.Expression;
import com.jeez.compiler.ast.expr.LiteralStringExpression;
import com.jeez.compiler.ast.expr.MultiplyExpression;
import com.jeez.compiler.ast.expr.NullExpression;
import com.jeez.compiler.ast.expr.OrExpression;
import com.jeez.compiler.ast.expr.UnaryExpression;
import com.jeez.compiler.lexer.Symbol;

public class JeezExpressionParser {

  private JeezParser jeezParser;
  
  public JeezExpressionParser(JeezParser jeezParser) {
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
      expr = new OrExpression(expr, right);
    }
    
    return expr;
  }
  
  private Expression parseAndExpression() {
    Expression expr = parseAddExpression();
    
    while (jeezParser.getToken() == AND) {
      jeezParser.nextToken();
      Expression right = parseAddExpression();
      expr = new AndExpression(expr, right);
    }
    
    return expr;
  }

  private Expression parseAddExpression() {
    Expression expr = parseMultiplyExpression();
    
    while (jeezParser.getToken() == PLUS || jeezParser.getToken() == MINUS) {
      Symbol operator = jeezParser.getToken();
      jeezParser.nextToken();
      
      Expression right = parseMultiplyExpression();
      expr = new AddExpression(expr, right, operator);
    }
    
    return expr;
  }

  private Expression parseMultiplyExpression() {
    Expression expr = parseUnaryExpression();
    
    while (jeezParser.getToken() == MULTIPLIER
        || jeezParser.getToken() == DIVISOR
        || jeezParser.getToken() == REMAINDER) {
      Symbol operator = jeezParser.getToken();
      jeezParser.nextToken();
      
      Expression right = parseUnaryExpression();
      expr = new MultiplyExpression(expr, right, operator);
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
      case NEW:
      case LEFT_BRACKET:
        throw new RuntimeException("Not yet implemented");
      
      case IDENTIFIER:
        return parseVariableOrMethodExpression();
      
      case LITERAL_STRING:
        return parseLiteralStringExpression();
    }
    
    throw new RuntimeException("Should throw an error here");
  }

  private Expression parseVariableOrMethodExpression() {
    throw new RuntimeException("Not yet implemented.");
  }
  
  private Expression parseLiteralStringExpression() {
    String stringLiteral = jeezParser.getStringLiteral();
    jeezParser.nextToken();
    
    return new LiteralStringExpression(stringLiteral);
  }
}
