package com.jeez.compiler.parser;

import static com.jeez.compiler.lexer.Symbol.ASSIGN;
import static com.jeez.compiler.lexer.Symbol.CLASS;
import static com.jeez.compiler.lexer.Symbol.COMMA;
import static com.jeez.compiler.lexer.Symbol.LEFT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.LEFT_PAR;
import static com.jeez.compiler.lexer.Symbol.RIGHT_CUR_BRACKET;
import static com.jeez.compiler.lexer.Symbol.RIGHT_PAR;
import static com.jeez.compiler.lexer.Symbol.STATIC;
import jeez.lang.Attribute;
import jeez.lang.Block;
import jeez.lang.ClassAttribute;
import jeez.lang.Clazz;
import jeez.lang.Method;
import jeez.lang.Type;
import jeez.lang.Variable;
import jeez.lang.builder.ClassBuilder;

public class ClassParser {
  
  private JeezParser jeezParser;
  
  private Clazz currentClass;
  
  private BlockParser blockParser;
  
  private ExpressionParser expressionParser;
  
  public ClassParser(JeezParser jeezParser) {
    this.jeezParser = jeezParser;
    this.blockParser = new BlockParser(jeezParser);
    this.expressionParser = new ExpressionParser(jeezParser);
  }
  
  Clazz parseClass() {
    jeezParser.expect(CLASS);
    
    currentClass = ClassBuilder.get().buildClass(jeezParser.parseIdentifier());
    
    jeezParser.expect(LEFT_CUR_BRACKET);
    
    while (jeezParser.getToken() != RIGHT_CUR_BRACKET) {
      if (jeezParser.getToken() == STATIC) {
        parseClassMember();
      } else {
        parseMember();
      }
    }
    
    jeezParser.expect(RIGHT_CUR_BRACKET);
    
    return currentClass;
  }
  
  private void parseClassMember() {
    jeezParser.expect(STATIC);
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {
      currentClass.addToClassMethods(parseMethod(type, name));
    } else {
      ClassAttribute attribute = new ClassAttribute(name);
      if (jeezParser.getToken() == ASSIGN) {
        jeezParser.nextToken();
        attribute.setInitialExpression(expressionParser.parseExpression());
      }
      currentClass.addToClassAttributes(attribute);
    }
  }

  private void parseMember() {
    Type type = jeezParser.parseType();
    String name = jeezParser.parseIdentifier();
    
    if (jeezParser.getToken() == LEFT_PAR) {
      currentClass.addToMethods(parseMethod(type, name));
    } else {
      currentClass.addToAttributes(new Attribute(name));
    } 
  }    
  
  private Method parseMethod(Type type, String methodName) {
    Method method = new Method(currentClass, type, methodName);
    
    jeezParser.expect(LEFT_PAR);
    if (jeezParser.getToken() != RIGHT_PAR) {
      parseParameterListFor(method);
    }
    jeezParser.nextToken();
    
    Block block = blockParser.parseBlock();
    method.setBlock(block);
    
    return method;
  }

  private void parseParameterListFor(Method method) {
    method.addToParameters(parseParameter());
    while (jeezParser.getToken() == COMMA) {
      jeezParser.nextToken();
      method.addToParameters(parseParameter());
    }
  }
  
  private Variable parseParameter() {
    return new Variable(jeezParser.parseIdentifier());
  }
}
