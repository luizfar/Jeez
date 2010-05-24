package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.COMMA;
import static jeez.interpreter.lexer.Symbol.LEFT_PAR;
import static jeez.interpreter.lexer.Symbol.RIGHT_PAR;
import jeez.lang.Block;
import jeez.lang.Function;
import jeez.lang.JeezClass;
import jeez.lang.Method;
import jeez.lang.Type;
import jeez.lang.Variable;

public class MethodParser {

  private MainParser mainParser;
  
  private BlockParser blockParser;
  
  public MethodParser(MainParser mainParser) {
    this.mainParser = mainParser;
    blockParser = new BlockParser(mainParser);
  }
  
  public Method parseMethod(JeezClass clazz, Type type, String methodName) {
    Method method = new Method(clazz, type, methodName);
    parseParametersAndBodyFor(method);
    return method;
  }
  
  private void parseParametersAndBodyFor(Function function) {
    mainParser.expect(LEFT_PAR);
    if (mainParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(function);
    }
    mainParser.nextToken();
    
    Block block = blockParser.parseBlock();
    function.setBlock(block);
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
