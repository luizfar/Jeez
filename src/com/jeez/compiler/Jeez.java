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
  
  

  static final String CODE =
    "class Dog {\n" +
    "  def name\n" +
    "  static def dogCount = 0\n" +
    "  def new(n) {\n" +
    "    name = n" +
    "    dogCount = dogCount + 1\n" +
    "    println \"inside of Dog's init\"\n" +
    "  }\n" +
    "" +
    "  def setName(n) {\n" +
    "    name = n\n" +
    "  }\n" +
    "" +
    "  def getName() {\n" +
    "    return name\n" +
    "  }\n" +
    "" +
    "  def bark() {\n" +
    "    println \"woof woof\"\n" +
    "  }\n" +
    "" +
    "  static def getCount(){\n" +
    "    return dogCount\n" +
    "  }\n" +
    "}\n" +
    "" +
    "println Dog.getCount()\n" +
    "def d = Dog.new(\"Totó\")\n" +
    "println Dog.getCount()\n" +
    "println d.getName()";
  
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
    context.prepare();
    Module module = context.getModule(MODULE_NAME + "_module");
    Function function = module.getFunction(Module.ANONYMOUS_FUNCTION_NAME);    
    function.execute(new JeezObject(), new ArrayList<Expression>(), context);
  }
}
