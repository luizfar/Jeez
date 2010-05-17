package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.DEF;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.RIGHT_PAR;
import static jeez.lang.Type.DUCK;
import jeez.lang.Block;
import jeez.lang.Function;
import jeez.lang.Variable;

public class FunctionParser {

  private JeezParser jeezParser;
  
  private BlockParser blockParser;
  
  public FunctionParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.blockParser = new BlockParser(jeezParser);
  }
  
  public Function parseFunction() {
    jeezParser.expect(DEF);
    Function function = new Function(DUCK, jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_PAR);
    if (jeezParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(function);
    }
    jeezParser.nextToken();
    
    Block block = blockParser.parseBlock();
    function.setBlock(block);
    
    return function;
  }

  private void parseParameterListFor(Function function) {
    function.addToParameters(parseParameter());
    while (jeezParser.getToken() == COMMA) {
      jeezParser.nextToken();
      function.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new Variable(jeezParser.parseIdentifier());
  }
}
