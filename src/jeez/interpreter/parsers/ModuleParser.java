package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.MODULE;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Module;

public class ModuleParser {
  
  private JeezParser jeezParser;
  
  private FunctionParser functionParser;
  
  public ModuleParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.functionParser = new FunctionParser(jeezParser);
  }
  
  public Module parseModule() {
    jeezParser.expect(MODULE);
    String moduleName = jeezParser.parseIdentifier();
    Module module = new Module(moduleName);
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      module.addToFunctions(functionParser.parseFunction());
    }
    jeezParser.nextToken();
    
    return module;
  }
}
