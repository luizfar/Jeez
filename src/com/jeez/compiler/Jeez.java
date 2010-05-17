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
    "  static def dogCount\n" +
    "  def setName(n) {\n" +
    "    name = n\n" +
    "  }\n" +
    "  def getName() {\n" +
    "    return name\n" +
    "  }\n" +
    "  def bark() {\n" +
    "    println \"woof woof\"\n" +
    "  }\n" +
    "  static def getCount(){\n" +
    "    return dogCount\n" +
    "  }\n" +
    "}\n" +
    "def d = Dog.new()\n" +
    "d.setName(\"Rex\")\n" +
    "println d.getName()\n" +
    "println Dog.getCount()\n" +
    "println Dog.getName()\n" +
    "if (d.understands(\"bark\")) {\n" +
    "  d.bark()\n" +
    "} else {\n" +
    "  println \"This dog can't bark\"\n" +
    "}\n" +
    "def D = d.getClass()\n" +
    "def C = D.getClass()\n" +
    "println C\n" +
    "def C1 = C.getClass()\n" +
    "println C1\n" +
    "\n" +
    "def clazz = C.new()\n" +
    "println clazz\n" +
    "if (clazz.understands(\"getName\")) {\n" +
    "  println clazz.getName()\n" +
    "} else {\n" +
    "  println \"doesnt understand\"\n" +
    "}\n";
  
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
