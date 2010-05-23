package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.COMMA;
import static jeez.interpreter.lexer.Symbol.DEF;
import static jeez.interpreter.lexer.Symbol.LEFT_PAR;
import static jeez.interpreter.lexer.Symbol.RIGHT_PAR;
import static jeez.lang.Type.DUCK;
import jeez.lang.Block;
import jeez.lang.Function;
import jeez.lang.Variable;

public class FunctionParser {

  private MainParser mainParser;
  
  private BlockParser blockParser;
  
  public FunctionParser(MainParser jeezParser) {
    this.mainParser = jeezParser;
    this.blockParser = new BlockParser(jeezParser);
  }
  
  public Function parseFunction() {
    mainParser.expect(DEF);
    Function function = new Function(DUCK, mainParser.parseIdentifier());
    
    mainParser.expect(LEFT_PAR);
    if (mainParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(function);
    }
    mainParser.nextToken();
    
    Block block = blockParser.parseBlock();
    function.setBlock(block);
    
    return function;
  }

  private void parseParameterListFor(Function function) {
    function.addToParameters(parseParameter());
    while (mainParser.getToken() == COMMA) {
      mainParser.nextToken();
      function.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new Variable(mainParser.parseIdentifier());
  }
}
