package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.ELSE;
import static com.jeez.compiler.lexer.Symbol.IF;
import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.PRINT;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.RIGHT_PAR;
import static com.jeez.compiler.lexer.Symbol.WHILE;

import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.MethodParameter;
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
  
  public JeezMethodBodyParser(JeezParser jeezParser, SymbolTable symbolTable) {
    this.jeezParser = jeezParser;
    this.symbolTable = symbolTable;
    exprParser = new JeezExpressionParser(jeezParser, symbolTable);
  }

  public void parseMethodBody(Method method) {
    exprParser.setCurrentMethod(method);
    symbolTable.clearLocalScope();
    for (MethodParameter parameter : method.getParameterList().getParameters()) {
      symbolTable.putInLocalScope(parameter);
    }
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
