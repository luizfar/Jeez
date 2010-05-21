package com.jeez.compiler;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class JeezTests {

  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outputStream));
  }
  
  @Test
  public void testBasicManipulation() throws Exception {
    String code = 
      "def i = 0\n" +
      "println i\n" +
      "println 1 + 2\n" +
      "println \"2\"\n" +
      "i = 10\n" +
      "println i\n" +
      "def j = i + i + 2\n" +
      "println j\n" +
      "println j == i\n";
    
    String expectedOutput = 
      "0\n" +
      "3\n" +
      "2\n" +
      "10\n" +
      "22\n" +
      "false\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testModule() throws Exception {
    String code = 
      "def i = 10" +
      "module Math {\n" +
      "  def add(i, b) {\n" +
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
      "println Math.sub(5, 3)\n";
    
    String expectedOutput = 
      "i before: 10\n" +
      "14\n" +
      "i after: 10\n" +
      "2\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testClass() throws Exception {
    String code = 
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
      "def clazz = C.new(\"Someclass\")\n" +
      "println clazz\n" +
      "println clazz.getName()\n" +
      "def someObject = clazz.new()\n" +
      "println someObject.getClass()";
    
    String expectedOutput = 
      "Rex\n" +
      "null\n" +
      "Dog\n" +
      "woof woof\n" +
      "Class Class\n" +
      "Class Class\n" +
      "Class Someclass\n" +
      "Someclass\n" +
      "Class Someclass\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testObjectsHaveDifferentInstanceVariables() throws Exception {
    String code = 
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
      "def d1 = Dog.new()\n" +
      "d1.setName(\"Rex\")\n" +
      "def d2 = Dog.new()\n" +
      "d2.setName(\"Totó\")\n" +
      "println d1.getName()\n" +
      "println d2.getName()\n";
    
    String expectedOutput = 
      "Rex\n" +
      "Totó\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testStatic() throws Exception {
    String code =
      "class Dog {\n" +
      "  static def count = 0\n" +
      "  def new() {\n" +
      "    count = count + 1\n" +
      "  }\n" +
      "  static def getCount() {\n" +
      "    return count\n" +
      "  }\n" +
      "  static def setCount(c) {\n" +
      "    count = c\n" +
      "  }\n" +
      "}\n" +
      "" +
      "println Dog.getCount()\n" +
      "def d = Dog.new()\n" +
      "println Dog.getCount()\n" +
      "Dog.setCount(2)\n" +
      "println Dog.getCount()\n";
    
    String expectedOutput = 
      "0\n" +
      "1\n" +
      "2\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test(expected = RuntimeException.class)
  public void testStaticMethodCanNotAccessInstanceVariables() throws Exception {
    String code =
      "class Dog {\n" +
      "  def name\n" +
      "  def new(n) {\n" +
      "    name = n\n" +
      "  }\n" +
      "  def getName() {\n" +
      "    return name\n" +
      "  }\n" +
      "  static def staticMethod() {\n" +
      "    println name\n" +
      "  }\n" +
      "}" +
      "" +
      "def d = Dog.new(\"Rex\")\n" +
      "println d.getName()\n" +
      "Dog.staticMethod()";
    
    String expectedOutput = 
      "";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testImplicitConstructor() throws Exception {
    String code =
      "class Dog {\n" +
      "  def name\n" +
      "  def getName() {\n" +
      "    return name\n" +
      "  }\n" +
      "}\n" +
      "" +
      "def d = Dog.new()\n" +
      "println d.getName()";
    
    String expectedOutput = 
      "null\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test(expected = RuntimeException.class)
  public void testImplicitConstructorHasNoArguments() throws Exception {
    String code =
      "class Dog {\n" +
      "  def name\n" +
      "  def getName() {\n" +
      "    return name\n" +
      "  }\n" +
      "}\n" +
      "" +
      "def d = Dog.new(\"Rex\")\n" +
      "println d.getName()";
    
    String expectedOutput = 
      "";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test
  public void testExplicitConstructor() throws Exception {
    String code =
      "class Dog{\n" +
      "  def name\n" +
      "  def new(n) {\n" +
      "    name = n\n" +
      "  }\n" +
      "  def getName() {\n" +
      "    return name\n" +
      "  }\n" +
      "}\n" +
      "" +
      "def d = Dog.new(\"Rex\")\n" +
      "println d.getName()\n";
    
    String expectedOutput = 
      "Rex\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  @Test(expected = RuntimeException.class)
  public void testDeclaringExplicitConstructorEliminatesImplicitOne() throws Exception {
    String code =
      "class Dog{\n" +
      "  def name\n" +
      "  def new(n) {\n" +
      "    name = n\n" +
      "  }\n" +
      "  def getName() {\n" +
      "    return name\n" +
      "  }\n" +
      "}\n" +
      "" +
      "def d = Dog.new()\n" +
      "println d.getName()\n";
    
    String expectedOutput = 
      "Rex\n";
    
    runAndAssert(expectedOutput, code);
  }
  
  public void testTemplate() throws Exception {
    String code =
      "";
    
    String expectedOutput = 
      "";
    
    runAndAssert(expectedOutput, code);
  }
  
  private void runAndAssert(String expectedOutput, String code) {
    Jeez jeez = new Jeez();
    jeez.run(code.toCharArray());
    assertEquals(expectedOutput, outputStream.toString());
  }
}
