package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.*;

import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.SymbolTable;
import com.jeez.compiler.ast.stmt.CompositeStatement;
import com.jeez.compiler.ast.stmt.IfStatement;
import com.jeez.compiler.ast.stmt.PrintStatement;
import com.jeez.compiler.ast.stmt.Statement;
import com.jeez.compiler.ast.stmt.StatementList;
import com.jeez.compiler.ast.stmt.WhileStatement;

public class JeezMethodBodyParser {

  private JeezParser jeezParser;
  
  private JeezExpressionParser exprParser;
  
  private SymbolTable symbolTable;
  
  public JeezMethodBodyParser(JeezParser jeezParser) {
    symbolTable = new SymbolTable();
    
    this.jeezParser = jeezParser;
    exprParser = new JeezExpressionParser(jeezParser, symbolTable);
  }

  public void parseMethodBody(Method method) {
    exprParser.setCurrentMethod(method);
    method.setStatementList(parseStatementList());
  }
  
  private StatementList parseStatementList() {
    StatementList statementList = new StatementList();
    statementList.addStatement(parseStatement());
    
    return statementList;
  }
  
  private Statement parseStatement() {
    switch (jeezParser.getToken()) {
      case IF:
        return parseIfStatement();
        
      case WHILE:
        return parseWhileStatement();
        
      case PRINT:
        return parsePrintStatement();
        
      case LEFT_CUR_BRACKET:
        return parseCompositeStatement();
    }
    
    throw new JeezParserException("'if', 'print' or '{' expected.", 
        jeezParser.getLineNumber());
  }
  
  private Statement parseIfStatement() {
    jeezParser.expect(IF);
    jeezParser.expect(LEFT_PAR);
    
    IfStatement statement = new IfStatement();
    statement.setExpression(exprParser.parseExpression());
    jeezParser.expect(RIGHT_PAR);
    
    statement.setIfStatement(parseStatement());
    
    if (jeezParser.getToken() == ELSE) {
      jeezParser.nextToken();
      statement.setElseStatement(parseStatement());
    }
    
    return statement;
  }
  
  private Statement parseWhileStatement() {
    jeezParser.expect(WHILE);
    jeezParser.expect(LEFT_PAR);
    
    WhileStatement statement = new WhileStatement();
    statement.setExpression(exprParser.parseExpression());
    jeezParser.expect(RIGHT_PAR);
    
    statement.setStatement(parseStatement());
    
    return statement;
  }
  
  private Statement parsePrintStatement() {
    jeezParser.expect(PRINT);
    
    PrintStatement statement = new PrintStatement();
    statement.addExpr(exprParser.parseExpression());
    
    while (jeezParser.getToken() == COMMA) {
      statement.addExpr(exprParser.parseExpression());
    }
    
    return statement;
  }
  
  private Statement parseCompositeStatement() {
    jeezParser.expect(LEFT_CUR_BRACKET);
    StatementList statementList = parseStatementList();
    jeezParser.expect(RIGHT_CUR_BRACKET);
    
    return new CompositeStatement(statementList);
  }
}
