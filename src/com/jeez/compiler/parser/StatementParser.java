package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.ASSIGN;
import static com.jeez.compiler.lexer.Symbol.DEF;
import static com.jeez.compiler.lexer.Symbol.ELSE;
import static com.jeez.compiler.lexer.Symbol.IDENTIFIER;
import static com.jeez.compiler.lexer.Symbol.IF;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.PRINT;
import static com.jeez.compiler.lexer.Symbol.PRINTLN;
import jeez.lang.expression.Expression;
import jeez.lang.statement.Assignment;
import jeez.lang.statement.DynamicVariableDeclaration;
import jeez.lang.statement.IfStatement;
import jeez.lang.statement.PrintStatement;
import jeez.lang.statement.PrintlnStatement;
import jeez.lang.statement.Statement;
import jeez.lang.statement.StatementList;
import jeez.lang.statement.VariableDeclaration;

public class StatementParser {

  private JeezParser jeezParser;
  
  private JeezExpressionParser exprParser;
  
  public StatementParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    exprParser = new JeezExpressionParser(jeezParser);
  }

  public Statement parseStatement() {
    switch (jeezParser.getToken()) {
    
      case IF:
        return parseIfStatement();
        
      case PRINT:
        return parsePrintStatement();
        
      case PRINTLN:
        return parsePrintlnStatement();
        
      case IDENTIFIER:
        return parseDeclarationOrAssignment();
        
      case DEF:
        return parseDynamicDeclaration();
    }
    
    throw new JeezParserException("'if', 'print' expected.", 
        jeezParser.getLineNumber());
  }
  
  private Statement parseIfStatement() {
    jeezParser.expect(IF);
    jeezParser.expect(LEFT_PAR);
    
    Expression expr = exprParser.parseExpression();
    
    Statement ifStatement = parseStatement();
    Statement elseStatement = null;
    
    if (jeezParser.getToken() == ELSE) {
      jeezParser.nextToken();
      elseStatement = parseStatement();
    }
    
    return new IfStatement(expr, ifStatement, elseStatement);
  }
  
  private Statement parsePrintStatement() {
    jeezParser.expect(PRINT);
    Expression expr = exprParser.parseExpression();
    
    return new PrintStatement(expr);
  }
  
  private Statement parsePrintlnStatement() {
    jeezParser.expect(PRINTLN);
    Expression expr = exprParser.parseExpression();
    
    return new PrintlnStatement(expr);
  }
  
  private Statement parseDeclarationOrAssignment() {
    String id1 = jeezParser.getStringValue();
    jeezParser.nextToken();
    
    if (jeezParser.getToken() == ASSIGN) {
      return parseAssignment(id1);
    }
    
    if (jeezParser.getToken() == IDENTIFIER) {
      return parseDeclaration(id1);
    }
    
    throw new RuntimeException("Id or = expected");
  }
  
  private Statement parseAssignment(String variableName) {
    jeezParser.expect(ASSIGN);
    Expression expr = exprParser.parseExpression();
    
    return new Assignment(variableName, expr);
  }
  
  private Statement parseDeclaration(String typeName) {
    String variableName = jeezParser.parseIdentifier();
    Statement statement = new VariableDeclaration(typeName, variableName);
    
    if (jeezParser.getToken() == ASSIGN) {
      StatementList list = new StatementList();
      list.addStatement(statement);
      list.addStatement(parseAssignment(variableName));
      statement = list;
    }
    
    return statement;
  }
  
  private Statement parseDynamicDeclaration() {
    jeezParser.expect(DEF);
    String variableName = jeezParser.parseIdentifier();
    Statement statement = new DynamicVariableDeclaration(variableName);
    
    if (jeezParser.getToken() == ASSIGN) {
      StatementList list = new StatementList();
      list.addStatement(statement);
      list.addStatement(parseAssignment(variableName));
      statement = list;
    }
    
    return statement;
  }
}
