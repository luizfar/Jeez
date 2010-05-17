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
  
  // TODO luiz implement stack for returned values and so on
  static final String CODE = 
    "def i = 0\n" +
    "println i\n" +
    "println 1 + 2\n" +
    "println \"2\"\n" +
    "i = 10\n" +
    "println i\n" +
    "def j = i + i + 2\n" +
    "println j\n" +
    "println j == i\n" +
    "module Math {\n" +
    "  def add(i, b) {\n" +
    "    print \"adding \"\n" +
    "    print i\n" +
    "    print \" and \"\n" +
    "    println b\n" +
    "    println i + b\n" +
    "  }\n" +
    "  def sub(a, b) {\n" +
    "    return a - b\n" +
    "  }\n" +
    "}\n" +
    "print \"i before: \"\n" +
    "println i\n" +
    "Math.add(12, 2)\n" +
    "print \"i after: \"\n" +
    "println i\n" +
    "println Math.sub(5, 3)\n" +
    "class Dog {\n" +
    "  def name\n" +
    "  def setName(n) {\n" +
    "    name = n\n" +
    "  }\n" +
    "  def getName() {\n" +
    "    return name\n" +
    "  }\n" +
    "}\n" +
    "def d = new Dog()\n" +
    "d.setName(\"Rex\")\n" +
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
    Module module = context.getModule(MODULE_NAME + "_module");
    Function function = module.getFunction(Module.ANONYMOUS_FUNCTION_NAME);
    function.execute(new JeezObject(), new ArrayList<Expression>(), context);
  }
}
