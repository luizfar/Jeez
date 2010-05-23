package jeez.interpreter.parsers;

import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET; 
import static jeez.interpreter.lexer.Symbol.MODULE;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Module;

public class ModuleParser {
  
  private MainParser mainParser;
  
  private FunctionParser functionParser;
  
  public ModuleParser(MainParser jeezParser) {
    this.mainParser = jeezParser;
    this.functionParser = new FunctionParser(jeezParser);
  }
  
  public Module parseModule() {
    mainParser.expect(MODULE);
    String moduleName = mainParser.parseIdentifier();
    Module module = new Module(moduleName);
    
    mainParser.expect(LEFT_CUR_BRACKET);
    while (mainParser.getToken() != RIGHT_CUR_BRACKET) {
      module.addToFunctions(functionParser.parseFunction());
    }
    mainParser.nextToken();
    
    return module;
  }
}
