package jeez.interpreter.parsers;

import static jeez.interpreter.execution.Bootstrap.getModuleClass;
import static jeez.interpreter.lexer.Symbol.LEFT_CUR_BRACKET;
import static jeez.interpreter.lexer.Symbol.MODULE;
import static jeez.interpreter.lexer.Symbol.RIGHT_CUR_BRACKET;
import jeez.lang.Method;
import jeez.lang.Module;
import jeez.lang.Type;

public class ModuleParser {
  
  private MainParser mainParser;
  
  private MethodParser methodParser;
  
  public ModuleParser(MainParser mainParser) {
    this.mainParser = mainParser;
    this.methodParser = new MethodParser(mainParser);
  }
  
  public Module parseModule() {
    mainParser.expect(MODULE);
    String moduleName = mainParser.parseIdentifier();
    Module module = new Module(moduleName);
    
    mainParser.expect(LEFT_CUR_BRACKET);
    while (mainParser.getToken() != RIGHT_CUR_BRACKET) {
      module.addToMethods(parseModuleMethod());
    }
    mainParser.nextToken();
    
    return module;
  }
  
  private Method parseModuleMethod() {
    Type type = mainParser.parseType();
    String name = mainParser.parseIdentifier();
    
    return methodParser.parseMethod(getModuleClass(), type, name);
  }
}
