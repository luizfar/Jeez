package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Block;

public class BlockParser {

  private JeezParser jeezParser;
  
  private StatementParser statementParser;
  
  public BlockParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.statementParser = new StatementParser(jeezParser);
  }
  
  public Block parseBlock() {
    Block block = new Block();
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      block.addToStatements(statementParser.parseStatement());
    }
    jeezParser.nextToken();
    
    return block;
  }
}
