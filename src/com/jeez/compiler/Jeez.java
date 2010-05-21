package com.jeez.compiler;

public class Jeez {

  
  
  
  
  
    static final String CODE =
    "class Dog {\n" +
    "  def name\n" +
    "  static def dogCount = 0\n" +
    "  def new(n) {\n" +
    "    name = n\n" +
    "    dogCount = dogCount + 1\n" +
    "  }\n" +
    "  \n" +
    "  def setName(n) {\n" +
    "    print \"Setting new name. Old name was: \"\n" +
    "    println getName()\n" +
    "    name = n\n" +
    "  }\n" +
    "  \n" +
    "  def getName() {\n" +
    "    return name\n" +
    "  }\n" +
    "  \n" +
    "  def bark() {\n" +
    "    println \"woof woof\"\n" +
    "  }\n" +
    "  \n" +
    "  static def getCount() {\n" +
    "    return dogCount\n" +
    "  }\n" +
    "}\n" +
    "\n" +
    "Dog d = Dog.new(\"Rex\")\n" +
    "String name = d.getName()\n" +
    "println name\n" +
    "def v = if name == \"Rex\" \"equals\"\n" +
    "println v\n" +
    "// println name.indexOf(\"e\")\n";
  
  public static void main(String[] args) {
    new Jeez().run(CODE.toCharArray());
  }
  
  public void run(char[] input) {
    new JeezInterpreter().start(input);
  }
}
