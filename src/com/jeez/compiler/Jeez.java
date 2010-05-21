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
    "" +
    "  def setName(n) {\n" +
    "    print \"Setting new name. Old name was: \"\n" +
    "    println getName()\n" +
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
    "def d = Dog.new(\"Rex\")\n" +
    "d.setName(\"Totó\")\n" +
    "def d2 = Dog.new(\"Floquinho\")\n" +
    "d2.setName(\"Rex\")\n";
  
  public static void main(String[] args) {
    new Jeez().run(CODE.toCharArray());
  }
  
  public void run(char[] input) {
    new JeezInterpreter().start(input);
  }
}
