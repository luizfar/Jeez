package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;

import java.util.ArrayList;
import java.util.List;

import com.jeez.compiler.ast.JeezClass;
import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.SymbolTable;
import com.jeez.compiler.ast.Variable;
import com.jeez.compiler.ast.expr.AddExpression;
import com.jeez.compiler.ast.expr.AndExpression;
import com.jeez.compiler.ast.expr.Expression;
import com.jeez.compiler.ast.expr.InstantiationExpression;
import com.jeez.compiler.ast.expr.IntegerExpression;
import com.jeez.compiler.ast.expr.LiteralBooleanExpression;
import com.jeez.compiler.ast.expr.LiteralStringExpression;
import com.jeez.compiler.ast.expr.MessageSendToThisExpression;
import com.jeez.compiler.ast.expr.MultiplyExpression;
import com.jeez.compiler.ast.expr.NullExpression;
import com.jeez.compiler.ast.expr.OrExpression;
import com.jeez.compiler.ast.expr.UnaryExpression;
import com.jeez.compiler.ast.expr.VariableExpression;
import com.jeez.compiler.lexer.Symbol;

public class JeezExpressionParser {

  private JeezParser jeezParser;
  
  private Method currentMethod;
  
  private SymbolTable symbolTable;
  
  public JeezExpressionParser(JeezParser jeezParser, SymbolTable symbolTable) {
    this.jeezParser = jeezParser;
    this.symbolTable = symbolTable;
  }
  
  public void setCurrentMethod(Method currentMethod) {
    this.currentMethod = currentMethod;
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
        return parseInstantiationExpression();
        
      case LEFT_BRACKET:
        throw new RuntimeException("Not yet implemented");
      
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
    
    String identifier = jeezParser.parseIdentifier();
    JeezClass clazz = symbolTable.getFromGlobalScope(identifier);
    
    if (clazz == null) {
      throw new JeezParserException("Could not find class with name '"
          + identifier + "'", jeezParser.getLineNumber());
    }
    
    jeezParser.expect(LEFT_PAR);
    // TODO luiz parse constructor arguments    
    jeezParser.expect(RIGHT_PAR);
    
    return new InstantiationExpression(clazz);
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
    jeezParser.expect(LEFT_PAR);
    
    List<Expression> arguments = new ArrayList<Expression>();
    boolean parseArgument = jeezParser.getToken() != RIGHT_PAR;
    while (parseArgument) {
      arguments.add(parseExpression());
      if (jeezParser.getToken() == COMMA) {
        jeezParser.nextToken();
      } else {
        parseArgument = false;
      }
    }
    jeezParser.expect(RIGHT_PAR);
    
    JeezClass clazz = currentMethod.getOwner();
    List<Method> matches = clazz.getMethod(messageName, arguments.size());
    
    if (matches.size() == 0) {
      throw new JeezParserException("Could not find matching method for name '"
          + messageName + "'", jeezParser.getLineNumber());
    }
    
    if (matches.size() > 1) {
      throw new RuntimeException("Not yet implemented.");
    }
    
    Method method = matches.get(0);
    
    return new MessageSendToThisExpression(method, arguments);
  }
  
  private Expression parseVariableExpression(String identifier) {
    Variable variable = symbolTable.getFromLocalScope(identifier);
    
    if (variable == null) {
      throw new JeezParserException("Variable '" + identifier
          + "' not declared.", jeezParser.getLineNumber());
    }
    
    return new VariableExpression(variable);
  }
  
  private Expression parseLiteralBooleanExpression() {
    boolean value = jeezParser.getToken() == TRUE;
    jeezParser.nextToken();
    
    return new LiteralBooleanExpression(value);
  }
  
  private Expression parseIntegerExpression() {
    int value = jeezParser.getIntegerValue();
    jeezParser.nextToken();
    
    return new IntegerExpression(value);
  }
  
  private Expression parseLiteralStringExpression() {
    String stringLiteral = jeezParser.getStringLiteral();
    jeezParser.nextToken();
    
    return new LiteralStringExpression(stringLiteral);
  }
}
