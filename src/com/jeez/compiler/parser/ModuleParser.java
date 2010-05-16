package com.jeez.compiler.parser;

import static jeez.lang.Module.ANONYMOUS_FUNCTION_NAME;
import jeez.lang.Function;
import jeez.lang.Module;

public class ModuleParser {
  
  private StatementParser statementParser;
  
  public ModuleParser(JeezParser jeezParser) {
    this.statementParser = new StatementParser(jeezParser);
  }
  
  public Module parseModule() {
    return null;
  }
  
  public void parseAnonymousModule(Module module) {
    Function anonymousFunction = module.getFunction(ANONYMOUS_FUNCTION_NAME);
    anonymousFunction.getBlock().addToStatements(statementParser.parseStatement());
  }
}
