package com.jeez.compiler;

import java.io.BufferedReader;
import java.io.FileReader;

public class Jeez {

  
  
  static final String CODE2 =
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
  
  public static void main(String[] args) throws Exception {
    Jeez jeez = new Jeez();
    
    String code = jeez.readFile("/home/luiz/j.jz");
    jeez.run(code.toCharArray());
  }
  
  String readFile(String path) throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    StringBuffer code = new StringBuffer();
    String line = reader.readLine();
    while (line != null) {
      code.append(line + "\n");
      line = reader.readLine();
    }
    
    return code.toString();
  }
  
  void run(char[] input) {
    new JeezInterpreter().start(input);
  }
}
