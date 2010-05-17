package com.jeez.compiler;

import java.util.ArrayList;

import jeez.lang.Function;
import jeez.lang.JeezObject;
import jeez.lang.Module;
import jeez.lang.context.ExecutionContext;
import jeez.lang.expression.Expression;

import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.parser.JeezParser;

public class Jeez {
  
  static final String MODULE_NAME = "main.jz";
  
  private JeezLexer lexer;
  
  private JeezParser parser;
  
  private ExecutionContext context = new ExecutionContext();
  
  public static void main(String[] args) {
    
  }
  
  public void run(char[] input) {
    lexer = new JeezLexer(input);
    parser = new JeezParser(MODULE_NAME, lexer, context);
    
    parser.start();
    runScript();
  }
  
  private void runScript() {
    Module module = context.getModule(MODULE_NAME + "_module");
    Function function = module.getFunction(Module.ANONYMOUS_FUNCTION_NAME);
    function.execute(new JeezObject(), new ArrayList<Expression>(), context);
  }
}
