package com.jeez.compiler;

import jeez.lang.Function;
import jeez.lang.Module;
import jeez.lang.context.ExecutionContext;

import com.jeez.compiler.lexer.JeezLexer;
import com.jeez.compiler.parser.JeezParser;

public class Jeez {
  
  static final String MODULE_NAME = "main.jz";
  
  static final String CODE = 
    "def i = 0\n" +
    "println i\n" +
    "println 1 + 2\n" +
    "println \"2\"\n" +
    "i = 10\n" +
    "println i\n" +
    "def j = i + i + 2\n" +
    "println j\n" +
    "println j == i\n";
  
  private JeezLexer lexer;
  
  private JeezParser parser;
  
  private ExecutionContext context = new ExecutionContext();
  
  public static void main(String[] args) {
    new Jeez().run(CODE.toCharArray());
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
    function.execute(new Object(), context);
  }
}
