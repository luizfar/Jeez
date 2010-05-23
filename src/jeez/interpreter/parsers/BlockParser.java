package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Block;

public class BlockParser {

  private MainParser mainParser;
  
  private ExpressionParser expressionParser;
  
  public BlockParser(MainParser jeezParser) {
    this.mainParser = jeezParser;
    this.expressionParser = new ExpressionParser(jeezParser);
  }
  
  public Block parseBlock() {
    Block block = new Block();
    
    mainParser.expect(LEFT_CUR_BRACKET);
    while (mainParser.getToken() != RIGHT_CUR_BRACKET) {
      block.addToExpressions(expressionParser.parseExpression());
    }
    mainParser.nextToken();
    
    return block;
  }
}
