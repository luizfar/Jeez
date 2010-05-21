package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Block;

public class BlockParser {

  private JeezParser jeezParser;
  
  private ExpressionParser expressionParser;
  
  public BlockParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.expressionParser = new ExpressionParser(jeezParser);
  }
  
  public Block parseBlock() {
    Block block = new Block();
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      block.addToExpressions(expressionParser.parseExpression());
    }
    jeezParser.nextToken();
    
    return block;
  }
}
