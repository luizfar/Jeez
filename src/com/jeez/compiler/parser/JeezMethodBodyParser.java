package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.PRINT;

import com.jeez.compiler.ast.Method;
import com.jeez.compiler.ast.SymbolTable;
import com.jeez.compiler.ast.stmt.PrintStatement;
import com.jeez.compiler.ast.stmt.Statement;
import com.jeez.compiler.ast.stmt.StatementList;

public class JeezMethodBodyParser {

  private JeezParser jeezParser;
  
  private JeezExpressionParser exprParser;
  
  private SymbolTable symbolTable;
  
  public JeezMethodBodyParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    exprParser = new JeezExpressionParser(jeezParser);
    symbolTable = new SymbolTable();
  }

  public void parseMethodBody(Method method) {
    method.setStatementList(parseStatementList());
  }
  
  private StatementList parseStatementList() {
    StatementList statementList = new StatementList();
    
    switch (jeezParser.getToken()) {
      case PRINT:
        statementList.addStatement(parsePrintStatement());
    }
    
    return statementList;
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
}
